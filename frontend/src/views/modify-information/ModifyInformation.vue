<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>修改个人信息</span>
      </div>
      <el-form :model="formData" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="formData.username" disabled></el-input> <!-- 用户名不可修改 -->
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="formData.name"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="formData.email"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="formData.phone"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="formData.studentNumber"></el-input>
        </el-form-item>
        <div style="text-align: right;">
          <el-button type="primary" @click="submitForm">保存修改</el-button>
          <el-button @click="resetForm">重置</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getUserInfo, updateUserInfo } from '@/api/student' // 假设API路径为@/api/user

export default {
  data() {
    return {
      formData: {
        username: '', // 用户名
        name: '', // 姓名
        email: '', // 邮箱
        phone: '', // 电话
        studentNumber: '' // 学号
      }
    }
  },
  created() {
    // 页面加载时获取用户信息
    this.fetchUserData();
  },
  methods: {
    // 获取用户数据
    fetchUserData() {
      getUserInfo().then(response => {
        this.formData = { ...response.data }; // 回显用户数据到表单
      }).catch(error => {
        this.$message.error('获取用户信息失败');
      });
    },
    // 提交修改后的表单数据
    submitForm() {
      updateUserInfo(this.formData).then(() => {
        this.$message.success('修改成功');
      }).catch(() => {
        this.$message.error('修改失败');
      });
    },
    // 重置表单为初始值
    resetForm() {
      this.fetchUserData(); // 重置回调后从服务器获取的原始数据
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
