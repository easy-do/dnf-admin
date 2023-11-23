const { contextBridge, ipcRenderer } = require('electron')

contextBridge.exposeInMainWorld('daGameClient', {
  startGame: () => {
    ipcRenderer.invoke('startGame')
  }
})