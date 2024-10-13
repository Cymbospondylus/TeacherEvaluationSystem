import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import store from "@/store";

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard'}
    }]
  },

  {
    path: '/test',
    component: Layout,
    children: [{
      path: 'test',
      name: '--------',
      meta: { title: '----管理员操作页面----', icon: 'el-icon-s-data'}
    }]
  },
  {
    path: '/management',
    component: Layout,
    redirect: '/management/admin',
    name: 'user-management',
    meta: { title: '用户管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'admin',
        name: 'admin-management',
        component: () => import('@/views/admin/AdminManagement.vue'),
        meta: { title: '管理员管理', icon: 'tree', roles: ['admin']}
      },
      {
        path: 'teacher',
        name: 'teacher-management',
        component: () => import('@/views/teacher/TeacherManagement.vue'),
        meta: { title: '老师管理', icon: 'tree', roles: ['admin']}
      },
      {
        path: 'student',
        name: 'teacher-management',
        component: () => import('@/views/student/StudentManagement.vue'),
        meta: { title: '学生管理', icon: 'tree', roles: ['admin']}
      }
    ]
  },

  {
    path: '/indicator',
    component: Layout,
    children: [
      {
        path: 'evaluation',
        name: 'teacher-evaluation',
        component: () => import('@/views/evaluation/TeacherEvaluation.vue'),
        meta: { title: '评价指标管理', icon: 'form', roles: ['admin']}
      }
    ]
  },


  {
    path: '/test',
    component: Layout,
    children: [{
      path: 'test',
      name: '--------',
      meta: { title: '----学生操作页面----', icon: 'el-icon-s-data'}
    }]
  },
  {
    path: '/evaluate',
    component: Layout,
    meta: {
      title: '评价老师',
      icon: 'nested'
    },
    children: [
      {
        path: 'teacher',
        component: () => import('@/views/evaluateTeacher/EvaluateTeacher.vue'),
        name: '评价老师',
        meta: { title: '评价老师', roles: ['student']}
      }
    ]
  },

  {
    path: '/modify-information',
    component: Layout,
    meta: {
      title: '修改个人信息',
      icon: 'link'
    },
    children: [
      {
        path: 'modify',
        component: () => import('@/views/modify-information/ModifyInformation.vue'),
        name: '修改个人信息',
        meta: { title: '修改个人信息', roles: ['student']}
      }
    ]
  },


  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

router.beforeEach((to, from, next) => {
  const userRole = store.getters.role; // 从 Vuex 中获取用户角色
  console.log(userRole);
  if (to.meta.roles && !to.meta.roles.includes(userRole)) {
    next('/404'); // 如果用户角色不在路由的 roles 中，重定向到 404
  } else {
    next(); // 否则继续导航
  }
});


export default router
