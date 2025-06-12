const { DataTypes } = require('sequelize');
const db = require('../config/db');
const bcrypt = require('bcryptjs');

const User = db.define('User', {
  username: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true
  },
  email: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true,
    validate: {
      isEmail: true
    }
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false,
    set(value) {
      const salt = bcrypt.genSaltSync(10);
      const hash = bcrypt.hashSync(value, salt);
      this.setDataValue('password', hash);
    }
  }
}, {
  timestamps: true,
  underscored: true,
  tableName: 'users'
});

// 关联关系
User.associate = function(models) {
  User.belongsToMany(models.Role, {
    through: 'user_roles',
    foreignKey: 'user_id',
    otherKey: 'role_id'
  });
};

module.exports = User;