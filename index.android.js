'use strict';

var { NativeModules } = require('react-native');
var NativeRNUpdate = NativeModules.RNUpdate

class RNUpdate {
  constructor() {
  }

  static update(url) {
    NativeRNUpdate.update(url);
  }
}

module.exports = RNUpdate;