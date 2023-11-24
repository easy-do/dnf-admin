const {
  app,
  BrowserWindow,
  Notification,
  ipcMain,
  Menu
} = require('electron')
const path = require('path')
const url = require('url')
const exec = require('child_process').exec


// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let win

function createWindow() {
  ipcMain.handle('startGame', (event, cmdStr) => {
    new Notification({
      title: 'DNF 启动!',
      body: 'DNF 启动!',
    }).show();
    let cmds = cmdStr.split(' ')
    exec(cmds[0] + '\\DNF.exe ' + cmds[1], { cwd: cmds[0] }, (err, stdout, stderr) => {
      if (err) {
        console.log(err)
        new Notification({
          title: '通知',
          body: '启动失败: ' + stdout,
        }).show();
        return
      }
    })
  })
  Menu.setApplicationMenu(null) // null值取消顶部菜单栏
  // Create the browser window.
  win = new BrowserWindow({
    width: 1100,
    height: 900,
    // frame: false, // 去掉窗口边框
    // titleBarStyle: 'hidden', // 隐藏标题栏
    webPreferences: {
      webSecurity: false,
      preload: path.join(__dirname, 'preload.js')
    }
  })

  // 生产环境
  win.loadURL(path.join('file://', __dirname, 'dist/index.html'))
  // 本地环境
  // win.loadURL('https://da.easydo.plus');
  // win.loadURL('http://localhost:8000');
  // win.openDevTools();
  // Open the DevTools.
  // win.webContents.openDevTools()

  // Emitted when the window is closed.
  win.on('closed', () => {
    // Dereference the window object, usually you would store windows
    // in an array if your app supports multi windows, this is the time
    // when you should delete the corresponding element.
    win = null
  })
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', () => {
  // On macOS it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  // On macOS it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (win === null) {
    createWindow()
  }
})

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.

