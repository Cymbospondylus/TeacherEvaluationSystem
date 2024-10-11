<template>
  <div class="app-container">
    <!-- 添加管理员按钮 -->
    <el-button type="primary" @click="handleAdd" style="margin-bottom: 20px;">添加管理员</el-button>

    <!-- 管理员列表表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <!-- 管理员 ID 列 -->
      <el-table-column
        prop="userId"
        label="管理员 ID"
        min-width="100">
      </el-table-column>

      <!-- 管理员姓名列 -->
      <el-table-column
        prop="username"
        label="用户名"
        min-width="150">
      </el-table-column>

      <!-- 管理员状态列 -->
      <el-table-column
        label="状态"
        min-width="100"
        align="center">
        <template slot-scope="scope">
          <el-tag :type="statusFilter(scope.row.status)">
            {{ statusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 管理员邮箱列 -->
      <el-table-column
        prop="email"
        label="邮箱"
        min-width="200"
        align="center">
      </el-table-column>

      <!-- 管理员电话列 -->
      <el-table-column
        prop="phone"
        label="电话"
        min-width="150"
        align="center">
      </el-table-column>

      <!-- 操作列，包含编辑和删除按钮 -->
      <el-table-column
        label="操作"
        min-width="180"
        align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加管理员弹窗 -->
    <el-dialog title="添加管理员" :visible.sync="dialogVisible">
      <el-form :model="formData" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="formData.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="formData.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="formData.email"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="formData.phone"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAdd">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchAdminList, addAdmin } from '@/api/admin'

export default {
  data() {
    return {
      list: [], // 数据列表
      listLoading: false, // 加载状态
      searchParams: {
        roleId: 1, // 角色 ID 查询参数
        size: 10,   // 每页显示条数
        current: 1  // 当前页码
      },
      dialogVisible: false, // 控制添加管理员弹窗的显示状态
      formData: { // 新管理员表单数据
        username: '',
        password:'',
        email: '',
        phone: ''
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 使用封装好的 API 请求方法获取数据
    fetchData() {
      this.listLoading = true
      const token = this.$store.state.user.token // 从 Vuex 中获取 token
      const params = {
        roleId: this.searchParams.roleId, // 查询角色 ID
        size: this.searchParams.size,
        current: this.searchParams.current
      }
      fetchAdminList(params, token)
        .then((response) => {
          const { code, data } = response
          if (code === 200) {
            this.list = data // 赋值数据到列表中
          }
        })
        .catch((error) => {
          console.error('获取数据失败:', error)
        })
        .finally(() => {
          this.listLoading = false // 请求结束，隐藏加载状态
        })
    },
    // 显示添加管理员弹窗
    handleAdd() {
      this.dialogVisible = true
    },
    // 提交添加管理员表单
    submitAdd() {
      const token = this.$store.state.user.token // 从 Vuex 中获取 token
      addAdmin(this.formData, token)
        .then((response) => {
          const { code, msg } = response
          if (code === 200) {
            this.$message.success('管理员添加成功')
            this.dialogVisible = false
            this.fetchData() // 重新获取管理员列表
          } else {
            this.$message.error(`添加失败: ${msg}`)
          }
        })
        .catch((error) => {
          this.$message.error(error)
        })
    },
    // 状态转换方法
    statusFilter(status) {
      return status === 0 ? 'success' : 'warning';
    },
    statusText(status) {
      return status === 0 ? '活跃' : '禁用';
    },
    // 编辑方法
    handleEdit(index, row) {
      console.log('编辑:', index, row);
    },
    // 删除方法
    handleDelete(index, row) {
      console.log('删除:', index, row);
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}
.dialog-footer {
  text-align: right;
}
</style>
