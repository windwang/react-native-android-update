'use strict';
import {NativeModules, AppState} from 'react-native'
let NativeRNUpdate = NativeModules.RNUpdate

function init(url) {
  return NativeRNUpdate.init(url);
}
function getNewVersion(){
	return NativeRNUpdate.getNewVersion()
}
function update(){
	return NativeRNUpdate.update()
}
module.exports = {
  init,getNewVersion,update
}
