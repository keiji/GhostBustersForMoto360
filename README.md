# ビルド方法 #

Android 4.4 (KitKat Wear: API 20)のSDK Platformを用意し、環境変数`ANDROID_HOME`を設定します。

    $ cd GhostBustersForMoto360
    $ $ ./gradlew assemble

# インストール方法 #

## Bluetooth経由のDebugを有効にしたMoto 360と接続する ##

    $ adb forward tcp:4444 localabstract:/adb-hub; adb connect localhost:4444
    connected to localhost:4444

## Moto 360にアプリをインストールする ##
    
    $ adb -s localhost:4444 install app/build/outputs/apk/app-debug.apk
     app/build/outputs/apk/app-debug.apk
    51 KB/s (1686964 bytes in 31.760s)
        pkg: /data/local/tmp/app-debug.apk
    Success

## サービスを起動する ##

    $ adb -s localhost:4444 shell am startservice io.keiji.ghostbusters/.WatchDockService
    Starting service: Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] cmp=io.keiji.ghostbusters/.WatchDockService }

この操作は、最初の一回だけ必要です。

次回からはMoto 360の起動時に自動的に起動します。