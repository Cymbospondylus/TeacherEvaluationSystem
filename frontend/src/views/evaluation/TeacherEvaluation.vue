<template>
  <div class="app-container">
    <!-- 添加评价指标按钮 -->
    <el-button type="primary" style="margin-bottom: 20px;" @click="handleAdd">添加评价指标</el-button>

    <!-- 评价指标列表表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      fit
      highlight-current-row
      style="width: 100%;">

      <!-- 指标 ID 列 -->
      <el-table-column
        prop="indicatorId"
        label="指标 ID"
        min-width="100" />

      <!-- 指标名称列 -->
      <el-table-column
        prop="name"
        label="指标名称"
        min-width="150" />

      <!-- 指标描述列 -->
      <el-table-column
        prop="description"
        label="描述"
        min-width="300" />

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

    <!-- 添加评价指标弹窗 -->
    <el-dialog title="添加评价指标" :visible.sync="dialogVisible">
      <el-form :model="formData" label-width="80px">
        <el-form-item label="指标名称">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="formData.description" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAdd">确定</el-button>
      </div>
    </el-dialog>

    <!-- 编辑评价指标弹窗 -->
    <el-dialog title="编辑评价指标" :visible.sync="editDialogVisible">
      <el-form :model="editData" label-width="80px">
        <el-form-item label="指标名称">
          <el-input v-model="editData.name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="editData.description" />
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
import { fetchIndicatorList, addIndicator, editIndicator, deleteIndicator } from '@/api/indicators'

export default {
  data() {
    return {
      list: [], // 评价指标列表数据
      listLoading: false, // 列表加载状态
      dialogVisible: false, // 控制添加弹窗显示状态
      formData: { // 新增评价指标表单数据
        name: '', // 指标名称
        description: '' // 描述
      },
      editDialogVisible: false, // 控制编辑弹窗显示状态
      editData: { // 编辑评价指标表单数据
        indicatorId: '', // 指标 ID
        name: '', // 指标名称
        description: '' // 描述
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 获取评价指标列表数据
    fetchData() {
      this.listLoading = true
      fetchIndicatorList().then((response) => {
        this.list = response.data;
      }).finally(() => {
        this.listLoading = false;
      })
    },
    // 添加评价指标
    handleAdd() {
      this.dialogVisible = true
    },
    submitAdd() {
      addIndicator(this.formData).then(() => {
        this.$message.success('评价指标添加成功')
        this.dialogVisible = false
        this.fetchData() // 重新获取评价指标列表
      }).catch(() => {
        this.$message.error('添加失败')
      })
    },
    // 编辑评价指标
    handleEdit(row) {
      this.editData = {...row}
      this.editDialogVisible = true
    },
    submitEdit() {
      editIndicator(this.editData).then(() => {
        this.$message.success('评价指标更新成功')
        this.editDialogVisible = false
        this.fetchData() // 重新获取评价指标列表
      }).catch(() => {
        this.$message.error('更新失败')
      })
    },
    // 删除评价指标
    handleDelete(row) {
      this.$confirm('确定删除该评价指标吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteIndicator(row.indicatorId).then(() => {
          this.$message.success('删除成功')
          this.fetchData() // 重新获取评价指标列表
        }).catch(() => {
          this.$message.error('删除失败')
        })
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
