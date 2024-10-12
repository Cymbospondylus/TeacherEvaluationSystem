<template>
  <div class="app-container">
    <!-- 添加学生按钮 -->
    <el-button type="primary" @click="handleAdd" style="margin-bottom: 20px;">添加学生</el-button>

    <!-- 学生列表表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <!-- 学生 ID 列 -->
      <el-table-column
        prop="studentId"
        label="学生 ID"
        min-width="100">
      </el-table-column>

      <!-- 学生姓名列 -->
      <el-table-column
        prop="studentName"
        label="学生姓名"
        min-width="150">
      </el-table-column>

      <!-- 学生状态列 -->
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

      <!-- 学生邮箱列 -->
      <el-table-column
        prop="email"
        label="邮箱"
        min-width="200"
        align="center">
      </el-table-column>

      <!-- 学生电话列 -->
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

    <!-- 添加学生弹窗 -->
    <el-dialog title="添加学生" :visible.sync="dialogVisible">
      <el-form :model="formData" label-width="80px">
        <el-form-item label="学生姓名">
          <el-input v-model="formData.studentName"></el-input>
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

    <!-- 编辑学生弹窗 -->
    <el-dialog title="编辑学生" :visible.sync="editDialogVisible">
      <el-form :model="editData" label-width="80px">
        <!-- 学生姓名（设为只读，不可修改） -->
        <el-form-item label="学生姓名">
          <el-input v-model="editData.studentName" disabled></el-input>
        </el-form-item>

        <!-- 邮箱 -->
        <el-form-item label="邮箱">
          <el-input v-model="editData.email"></el-input>
        </el-form-item>

        <!-- 电话 -->
        <el-form-item label="电话">
          <el-input v-model="editData.phone"></el-input>
        </el-form-item>

        <!-- 状态选项 -->
        <el-form-item label="状态">
          <el-select v-model="editData.status" placeholder="请选择状态">
            <el-option label="启用" value="enabled"></el-option>
            <el-option label="禁用" value="disabled"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEdit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchStudentList, addStudent, editStudent, deleteStudent } from '@/api/student'

export default {
  data() {
    return {
      list: [], // 数据列表
      listLoading: false, // 加载状态
      searchParams: {
        size: 10,   // 每页显示条数
        current: 1  // 当前页码
      },
      dialogVisible: false, // 控制添加学生弹窗的显示状态
      formData: { // 新学生表单数据
        studentName: '',
        email: '',
        phone: ''
      },
      editDialogVisible: false,
      editData: {
        studentId: '',
        studentName: '',
        email: '',
        phone: '',
        status: ''
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
        size: this.searchParams.size,
        current: this.searchParams.current
      }
      fetchStudentList(params, token)
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
    // 显示添加学生弹窗
    handleAdd() {
      this.dialogVisible = true
    },
    // 提交添加学生表单
    submitAdd() {
      const token = this.$store.state.user.token // 从 Vuex 中获取 token
      addStudent(this.formData, token)
        .then((response) => {
          const { code, msg } = response
          if (code === 200) {
            this.$message.success('学生添加成功')
            this.dialogVisible = false
            this.fetchData() // 重新获取学生列表
          } else {
            this.$message.error(`添加失败: ${msg}`)
          }
        })
        .catch((error) => {
          this.$message.error(error)
        })
    },
    // 编辑方法
    handleEdit(index, row) {
      this.editData = { ...row, status: row.status === 0 ? 'enabled' : 'disabled' }
      this.editDialogVisible = true
    },
    submitEdit() {
      const token = this.$store.state.user.token
      editStudent(this.editData, token)
        .then((response) => {
          const { code, msg } = response
          if (code === 200) {
            this.$message.success('学生信息编辑成功')
            this.editDialogVisible = false
            this.fetchData()
          } else {
            this.$message.error(`编辑失败: ${msg}`)
          }
        })
        .catch((error) => {
          this.$message.error(`编辑请求失败: ${error}`)
        })
    },
    // 删除方法
    handleDelete(index, row) {
      const token = this.$store.state.user.token
      this.$confirm('确定删除该学生信息吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteStudent(row.studentId, token)
          .then((response) => {
            const { code, msg } = response
            if (code === 200) {
              this.$message.success('学生删除成功')
              this.fetchData()
            } else {
              this.$message.error(`删除失败: ${msg}`)
            }
          })
          .catch((error) => {
            this.$message.error(error)
          })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
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
