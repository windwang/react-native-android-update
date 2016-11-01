'use strict';
import {NativeModules, AppState} from 'react-native'
let NativeRNUpdate = NativeModules.RNUpdate

function init(url) {
  return Promise.resolve(null)
}
function getNewVersion(){
	return Promise.resolve(null)
}
function update(){
	
}
module.exports = {
  init,getNewVersion,update
}
