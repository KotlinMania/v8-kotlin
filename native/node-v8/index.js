const path = require('path');
let addon = null;
try {
    addon = require('./build/Release/v8_native.node');
} catch (e) {
    try {
        addon = require('./build/Debug/v8_native.node');
    } catch (e2) {
        throw new Error('Failed to load native v8_native.node addon: ' + e.message);
    }
}
module.exports = addon;
