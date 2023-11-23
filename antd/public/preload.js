const { contextBridge } = require('electron')
const exec = require('child_process').exec

contextBridge.exposeInMainWorld('daGameClient', {
  startGame: (token) => {
    let path = localStorage.getItem('daClientPath');
    exec(path + ' ' + token);
  }
})