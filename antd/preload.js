const { contextBridge, ipcRenderer } = require('electron')

contextBridge.exposeInMainWorld('daGameClient', {
  startGame: (cmdStr) => {
    ipcRenderer.invoke('startGame',cmdStr)
  }
})