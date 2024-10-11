<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <!-- 管理员 ID 列 -->
      <el-table-column align="center" label="管理员 ID" width="95">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>

      <!-- 管理员姓名列 -->
      <el-table-column label="姓名" width="150">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>

      <!-- 管理员角色列 -->
      <el-table-column label="角色" width="150" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.role }}</span>
        </template>
      </el-table-column>

      <!-- 管理员状态列 -->
      <el-table-column label="状态" width="110" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>

      <!-- 管理员创建时间列 -->
      <el-table-column align="center" prop="created_at" label="创建时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.created_at }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  filters: {
    // 状态过滤器，用于映射状态到不同颜色标签
    statusFilter(status) {
      const statusMap = {
        active: 'success',
        inactive: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      // 模拟数据列表
      list: [
        {id: 1, name: 'Admin A', role: 'Super Admin', status: 'active', created_at: '2023-01-15'},
        {id: 2, name: 'Admin B', role: 'Editor', status: 'inactive', created_at: '2023-05-20'},
        {id: 3, name: 'Admin C', role: 'Moderator', status: 'deleted', created_at: '2023-03-10'},
        {id: 4, name: 'Admin D', role: 'Admin', status: 'active', created_at: '2023-02-22'}
      ],
      listLoading: false  // 初始时不显示加载状态
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
