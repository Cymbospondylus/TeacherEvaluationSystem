<template>
  <div class="dashboard-container">
    <!-- 修改密码按钮 -->
    <el-button type="primary" @click="showPasswordDialog = true">修改密码</el-button>

    <!-- 修改密码弹窗 -->
    <el-dialog title="修改密码" :visible.sync="showPasswordDialog" width="400px">
      <el-form :model="passwordForm" :rules="rules" ref="passwordForm" label-width="100px">
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showPasswordDialog = false">取消</el-button>
        <el-button type="primary" @click="submitPassword">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { updatePassword } from '@/api/user' // 引入修改密码的 API 方法

export default {
  name: 'Dashboard',
  data() {
    return {
      showPasswordDialog: false, // 控制弹窗显示
      passwordForm: { // 存放密码表单数据
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      rules: { // 表单校验规则
        oldPassword: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '新密码长度不能小于6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { validator: (rule, value, callback) => {
              if (value !== this.passwordForm.newPassword) {
                callback(new Error('两次输入的新密码不一致'))
              } else {
                callback()
              }
            }, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapGetters(['name']) // 从 Vuex 中获取用户名
  },
  methods: {
    // 提交修改密码
    submitPassword() {
      this.$refs.passwordForm.validate((valid) => {
        if (valid) {
          // 调用修改密码 API
          updatePassword({
            oldPassword: this.passwordForm.oldPassword,
            newPassword: this.passwordForm.newPassword
          }).then(response => {
            const { code, msg } = response
            if (code === 200) {
              this.$message.success('密码修改成功')
              this.showPasswordDialog = false // 关闭弹窗
              this.resetForm() // 重置表单数据
            } else {
              this.$message.error(`密码修改失败: ${msg}`)
            }
          }).catch(error => {
            this.$message.error(`请求错误: ${error}`)
          })
        } else {
          console.log('表单校验失败')
          return false
        }
      })
    },

    // 重置表单
    resetForm() {
      this.passwordForm = {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
  .dialog-footer {
    text-align: right;
  }
}
</style>
