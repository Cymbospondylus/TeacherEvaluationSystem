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

      <!-- 操作列，包含编辑、删除和查看评价按钮 -->
      <el-table-column
        label="操作"
        min-width="250"
        align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          <el-button size="mini" type="success" @click="viewEvaluation(scope.row)">查看评价</el-button>
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

    <!-- 查看评价结果弹窗 -->
    <el-dialog :title="`教师评价结果 - ${currentTeacher.name}`" :visible.sync="evaluationDialogVisible" width="70%">
      <!-- ECharts 图表容器 -->
      <div id="evaluation-chart" style="width: 100%; height: 400px;"></div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="evaluationDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { fetchTeacherList, addTeacher, editTeacher, deleteTeacher } from '@/api/teacher'
import { fetchTeacherEvaluations } from '@/api/evaluation'

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
      },
      evaluationDialogVisible: false, // 控制查看评价结果弹窗显示
      evaluationResults: [], // 评价结果数据
      evaluationLoading: false, // 评价结果加载状态
      currentTeacher: {}, // 当前选中的教师信息
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
    // 查看教师评价结果并绘制图表
    viewEvaluation(row) {
      this.currentTeacher = row;
      this.evaluationDialogVisible = true;
      this.evaluationLoading = true;

      // 获取评价数据
      fetchTeacherEvaluations(row.teacherId).then((response) => {
        const data = response.data;
        const indicatorNames = [];
        const averageScores = [];

        // 遍历每个指标，获取名称和平均分
        Object.keys(data).forEach(indicator => {
          indicatorNames.push(data[indicator].indicator_name);
          averageScores.push(data[indicator].average_score);
        });

        // 调用函数绘制图表
        this.drawChart(indicatorNames, averageScores);
      }).finally(() => {
        this.evaluationLoading = false;
      });
    },

    drawChart(indicatorNames, averageScores) {
      const chartDom = document.getElementById('evaluation-chart');
      const myChart = echarts.init(chartDom);

      const option = {
        title: {
          text: '教师评价结果',
          left: 'center',
          textStyle: {
            color: '#333',
            fontSize: 20
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow' // 鼠标悬停时显示阴影
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: indicatorNames,
          axisLabel: {
            interval: 0,
            rotate: 30, // 旋转标签避免重叠
            color: '#333',
            fontSize: 12
          },
          axisLine: {
            lineStyle: {
              color: '#ccc'
            }
          }
        },
        yAxis: {
          type: 'value',
          min: 0,
          max: 5,
          axisLabel: {
            formatter: '{value} 分',
            color: '#333',
            fontSize: 12
          },
          splitLine: {
            lineStyle: {
              type: 'dashed' // 使用虚线显示网格
            }
          },
          axisLine: {
            lineStyle: {
              color: '#ccc'
            }
          }
        },
        series: [{
          name: '平均分',
          type: 'bar',
          data: averageScores,
          barWidth: '50%', // 控制柱子的宽度
          itemStyle: {
            color: new echarts.graphic.LinearGradient(
              0, 0, 0, 1, // 垂直渐变
              [
                { offset: 0, color: '#83bff6' },
                { offset: 0.5, color: '#188df0' },
                { offset: 1, color: '#188df0' }
              ]
            ),
            shadowColor: 'rgba(0, 0, 0, 0.3)',
            shadowBlur: 10
          },
          label: {
            show: true,
            position: 'top',
            formatter: '{c} 分',
            color: '#333',
            fontSize: 12
          }
        }]
      };

      // 使用刚指定的配置项和数据显示图表
      myChart.setOption(option);
    }
    ,
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
