'use strict';
import {NativeModules, AppState} from 'react-native'
let NativeRNUpdate = NativeModules.RNUpdate

function start(url) {
  NativeRNUpdate.update(url);

}
module.exports = {
  start
}
