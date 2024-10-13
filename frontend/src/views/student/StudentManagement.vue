<template>
  <div class="app-container">
    <!-- 添加学生按钮 -->
    <el-button type="primary" style="margin-bottom: 20px;" @click="handleAdd">添加学生</el-button>

    <!-- 学生列表表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      fit
      highlight-current-row
      style="width: 100%;">

      <!-- 用户名列 -->
      <el-table-column
        prop="username"
        label="用户名"
        min-width="150" />

      <!-- 学生姓名列 -->
      <el-table-column
        prop="name"
        label="学生姓名"
        min-width="150" />

      <!-- 学号列 -->
      <el-table-column
        prop="studentNumber"
        label="学号"
        min-width="150" />

      <!-- 学生邮箱列 -->
      <el-table-column
        prop="email"
        label="邮箱"
        min-width="200" />

      <!-- 学生电话列 -->
      <el-table-column
        prop="phone"
        label="电话"
        min-width="150" />

      <!-- 创建时间列 -->
      <el-table-column
        prop="createTime"
        label="创建时间"
        min-width="180">
        <template slot-scope="scope">
          {{ formatDate(scope.row.createTime) }}
        </template>
      </el-table-column>

      <!-- 修改时间列 -->
      <el-table-column
        prop="updateTime"
        label="修改时间"
        min-width="180">
        <template slot-scope="scope">
          {{ formatDate(scope.row.updateTime) }}
        </template>
      </el-table-column>

      <!-- 操作列，包含编辑和删除按钮 -->
      <el-table-column
        label="操作"
        min-width="180"
        align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加学生弹窗 -->
    <el-dialog title="添加学生" :visible.sync="dialogVisible">
      <el-form :model="formData" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="formData.username" />
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="formData.studentNumber" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="formData.email" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="formData.phone" />
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
        <el-form-item label="用户名">
          <el-input v-model="editData.username" disabled />
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="editData.name"  />
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="editData.studentNumber"  />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editData.email" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="editData.phone" />
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
      list: [], // 学生列表数据
      listLoading: false, // 列表加载状态
      dialogVisible: false, // 控制添加弹窗显示状态
      formData: { // 新增学生表单数据
        username: '', // 用户名
        name: '', // 学生姓名
        studentNumber: '', // 学号
        email: '', // 邮箱
        phone: '' // 电话
      },
      editDialogVisible: false, // 控制编辑弹窗显示状态
      editData: { // 编辑学生表单数据
        userId: '', // 用户 ID
        username: '', // 用户名
        name: '', // 学生姓名
        studentNumber: '', // 学号
        email: '',
        phone: ''
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 获取学生列表数据
    fetchData() {
      this.listLoading = true
      // 构造分页参数
      const params = {
        size: this.pageSize || 10, // 每页显示条数，默认 10
        current: this.currentPage || 1, // 当前页，默认 1
        roleId: 2
      };
      fetchStudentList(params).then((response) => {
        this.list = response.data.filter(item => item !== null); // 过滤掉 null 数据
      }).finally(() => {
        this.listLoading = false;
      })
    },
    // 添加学生
    handleAdd() {
      this.dialogVisible = true
    },
    submitAdd() {
      addStudent(this.formData).then(() => {
        this.$message.success('学生添加成功')
        this.dialogVisible = false
        this.fetchData() // 重新获取学生列表
      }).catch(() => {
        this.$message.error('添加失败')
      })
    },
    // 编辑学生
    handleEdit(row) {
      this.editData = {...row}
      this.editDialogVisible = true
    },
    submitEdit() {
      editStudent(this.editData).then(() => {
        this.$message.success('学生信息更新成功')
        this.editDialogVisible = false
        this.fetchData() // 重新获取学生列表
      }).catch(() => {
        this.$message.error('更新失败')
      })
    },
    // 删除学生
    handleDelete(row) {
      this.$confirm('确定删除该学生信息吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteStudent(row.userId).then(() => {
          this.$message.success('删除成功')
          this.fetchData() // 重新获取学生列表
        }).catch(() => {
          this.$message.error('删除失败')
        })
      })
    },
    // 格式化日期
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleString(); // 返回本地时间格式
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
