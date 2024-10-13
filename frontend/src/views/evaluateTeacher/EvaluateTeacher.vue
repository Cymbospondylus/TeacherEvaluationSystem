<template>
  <div class="app-container">
    <!-- 教师评价表格 -->
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



      <!-- 所教课程列 -->
      <el-table-column
        label="所教课程"
        min-width="150">
        <template slot-scope="scope">
          数学
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column
        label="操作"
        min-width="180"
        align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="openEvaluationDialog(scope.row)">评价</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 评价弹窗 -->
    <el-dialog title="评价教师" :visible.sync="dialogVisible">
      <el-form label-width="100px">
        <!-- 循环显示评价指标 -->
        <el-form-item
          v-for="indicator in indicators"
          :key="indicator.indicatorId"
          :label="indicator.name">
          <el-radio-group v-model="evaluationForm[indicator.indicatorId]">
            <el-radio v-for="score in 5" :label="score" :key="score">{{ score }} 分</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEvaluation">提交评价</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchTeachers, submitEvaluation, fetchIndicators } from '@/api/evaluation'

export default {
  data() {
    return {
      list: [], // 教师列表数据
      indicators: [], // 评价指标列表
      evaluationForm: {}, // 存储每个指标的评分
      currentTeacher: null, // 当前正在评价的教师
      dialogVisible: false, // 控制评价弹窗显示
      listLoading: false, // 列表加载状态
    }
  },
  created() {
    this.fetchData()
    this.fetchIndicators()
  },
  methods: {
    // 获取需要评价的教师列表
    fetchData() {
      this.listLoading = true
      fetchTeachers().then((response) => {
        this.list = response.data
      }).finally(() => {
        this.listLoading = false
      })
    },
    // 获取评价指标
    fetchIndicators() {
      fetchIndicators().then((response) => {
        this.indicators = response.data
      })
    },
    // 打开评价弹窗
    openEvaluationDialog(teacher) {
      this.currentTeacher = teacher
      this.evaluationForm = {} // 清空之前的评价数据
      this.dialogVisible = true
    },
    // 提交评价
    submitEvaluation() {
      const evaluations = Object.keys(this.evaluationForm).map(indicatorId => ({
        teacherId: this.currentTeacher.teacherId,
        indicatorId: parseInt(indicatorId),
        score: this.evaluationForm[indicatorId]
      }))

      // 提交每个指标的评分
      evaluations.forEach(evaluationData => {
        submitEvaluation(evaluationData).then(() => {
          this.$message.success('评价提交成功')
        }).catch(() => {
          this.$message.error('评价提交失败')
        })
      })

      this.dialogVisible = false // 关闭弹窗
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
