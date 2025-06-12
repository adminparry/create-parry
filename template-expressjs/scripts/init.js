// scripts/initRoles.js
const { Role, Permission } = require('../model/Rbac');

const roles = [
  { name: 'admin', description: 'Administrator with full access' },
  { name: 'editor', description: 'Editor with limited access' },
  { name: 'viewer', description: 'Viewer with read-only access' }
];

const permissions = [
  { name: 'user_create', description: 'Create users', endpoint: '/users', method: 'POST' },
  { name: 'user_read', description: 'Read users', endpoint: '/users', method: 'GET' },
  { name: 'user_update', description: 'Update users', endpoint: '/users/:id', method: 'PUT' },
  { name: 'user_delete', description: 'Delete users', endpoint: '/users/:id', method: 'DELETE' }
];

async function init() {
  try {
    // 创建角色
    const createdRoles = await Role.bulkCreate(roles, { returning: true });
    
    // 创建权限
    const createdPermissions = await Permission.bulkCreate(permissions, { returning: true });
    
    // 为admin角色分配所有权限
    const adminRole = createdRoles.find(r => r.name === 'admin');
    await adminRole.addPermissions(createdPermissions);
    
    // 为editor角色分配读写权限
    const editorRole = createdRoles.find(r => r.name === 'editor');
    await editorRole.addPermissions([
      createdPermissions.find(p => p.name === 'user_read'),
      createdPermissions.find(p => p.name === 'user_create')
    ]);
    
    console.log('RBAC initialization completed successfully');
  } catch (error) {
    console.error('Error initializing RBAC:', error);
  }
}

init();