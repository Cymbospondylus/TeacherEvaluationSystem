<template>
  <div class="app-container">
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
        label="姓名"
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
  </div>
</template>

<script>
// 导入封装的 API 方法
import { fetchAdminList } from '@/api/admin'

export default {
  data() {
    return {
      list: [], // 数据列表
      listLoading: false, // 加载状态
      searchParams: {
        roleId: '', // 角色 ID 查询参数
        size: 10,   // 每页显示条数
        current: 1  // 当前页码
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
        roleId: 1, // 查询角色 ID
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
</style>
