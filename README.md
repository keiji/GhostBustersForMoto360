#apkのダウンロード

https://github.com/keiji/GhostBustersForMoto360/releases

上記アドレスから最新のAPKファイルをダウンロードします。

#インストール方法

##Bluetooth経由のDebugを有効にしたMoto 360と接続する

    $ adb forward tcp:4444 localabstract:/adb-hub; adb connect 127.0.0.1:4444
    connected to 127.0.0.1:4444

##Moto 360にアプリをインストールする
    
    $ adb -s 127.0.0.1:4444 install app/build/outputs/apk/app-release-1_0_1.apk
     app/build/outputs/apk/app-release-1_0_1.apk
    40 KB/s (1123507 bytes in 26.994s)
	    pkg: /data/local/tmp/app-release-1_0_1.apk
    Success

##サービスを起動する

    $ adb -s 127.0.0.1:4444 shell am startservice io.keiji.ghostbusters/.WatchDockService
    Starting service: Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] cmp=io.keiji.ghostbusters/.WatchDockService }

この操作は、最初の一回だけ必要です。次回からはMoto 360の起動時に自動的に起動します。

##アプリをアンインストールする
    
    $ adb -s 127.0.0.1:4444 uninstall io.keiji.ghostbusters
