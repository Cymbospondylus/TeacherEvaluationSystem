<template>
  <div class="app-container">
    <!-- 添加教师按钮 -->
    <el-button type="primary" style="margin-bottom: 20px;" @click="handleAdd">添加教师</el-button>

    <!-- 教师列表表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      fit
      highlight-current-row
      style="width: 100%;">

      <!-- 教师 ID 列 -->
      <el-table-column
        prop="teacherId"
        label="教师 ID"
        min-width="100" />

      <!-- 教师姓名列 -->
      <el-table-column
        prop="name"
        label="教师姓名"
        min-width="150" />

      <!-- 教师邮箱列 -->
      <el-table-column
        prop="email"
        label="邮箱"
        min-width="200" />

      <!-- 教师电话列 -->
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

    <!-- 添加教师弹窗 -->
    <el-dialog title="添加教师" :visible.sync="dialogVisible">
      <el-form :model="formData" label-width="80px">
        <el-form-item label="教师姓名">
          <el-input v-model="formData.name" />
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

    <!-- 编辑教师弹窗 -->
    <el-dialog title="编辑教师" :visible.sync="editDialogVisible">
      <el-form :model="editData" label-width="80px">
        <el-form-item label="教师姓名">
          <el-input v-model="editData.name" disabled />
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
import { fetchTeacherList, addTeacher, editTeacher, deleteTeacher } from '@/api/teacher'

export default {
  data() {
    return {
      list: [], // 教师列表数据
      listLoading: false, // 列表加载状态
      dialogVisible: false, // 控制添加弹窗显示状态
      formData: { // 新增教师表单数据
        name: '',
        email: '',
        phone: ''
      },
      editDialogVisible: false, // 控制编辑弹窗显示状态
      editData: { // 编辑教师表单数据
        teacherId: '',
        name: '',
        email: '',
        phone: ''
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 获取教师列表数据
    fetchData() {
      this.listLoading = true
      // 构造分页参数
      const params = {
        size: this.pageSize || 10, // 每页显示条数，默认 10
        current: this.currentPage || 1 // 当前页，默认 1
      };
      fetchTeacherList(params).then((response) => {
        this.list = response.data.records;
        this.total = response.data.total; // 假设后端返回了 total 数据
      }).finally(() => {
        this.listLoading = false;
      })
    },
    // 添加教师
    handleAdd() {
      this.dialogVisible = true
    },
    submitAdd() {
      addTeacher(this.formData).then(() => {
        this.$message.success('教师添加成功')
        this.dialogVisible = false
        this.fetchData() // 重新获取教师列表
      }).catch(() => {
        this.$message.error('添加失败')
      })
    },
    // 编辑教师
    handleEdit(row) {
      this.editData = { ...row }
      this.editDialogVisible = true
    },
    submitEdit() {
      editTeacher(this.editData).then(() => {
        this.$message.success('教师信息更新成功')
        this.editDialogVisible = false
        this.fetchData() // 重新获取教师列表
      }).catch(() => {
        this.$message.error('更新失败')
      })
    },
    // 删除教师
    handleDelete(row) {
      this.$confirm('确定删除该教师信息吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteTeacher(row.teacherId).then(() => {
          this.$message.success('删除成功')
          this.fetchData() // 重新获取教师列表
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
