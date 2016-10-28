'use strict';
import {NativeModules, AppState} from 'react-native'
let NativeRNUpdate = NativeModules.RNUpdate
let started = false;
let checkUrl = null;
function update(url) {
  NativeRNUpdate.update(url);
}

function onAppStateChange(newState) {
  newState === "active" && NativeRNUpdate.update(url)
}

function start(url) {
  if (started) 
    return;
  started = true;
  checkUrl = url;

  AppState.addEventListener("change", onAppStateChange);
}
module.exports = {
  start,
  update
}