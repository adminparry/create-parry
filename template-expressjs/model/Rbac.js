const { DataTypes } = require('sequelize');

const db = require('../config/db');

const User = db.define(
    'User',
    {
        // Model attributes are defined here
        firstName: {
            type: DataTypes.STRING,
            allowNull: false,
        },
        lastName: {
            type: DataTypes.STRING,
            // allowNull defaults to true
        },
    },
    {
        // Other model options go here
    },
);