# ふつうのLinuxプログラミング第2版

## リンク一覧

- [対象本](https://www.amazon.co.jp/dp/B075ST51Y5)
- [サポートサイト](https://i.loveruby.net/stdlinux2/)
- [Docker環境構築](https://www.isoroot.jp/blog/4714/)

## Dockerでubuntu環境構築

・Dockerfileをビルドしてイメージを作成

```terminal
docker build -t ubuntu .
```

・ubuntu環境を立ち上げる

bind mountしてあげるとローカル作業ディレクトリとDocker環境で同期されるので便利

```terminal
docker run -it --volume $(pwd):/app ubuntu
```

## 2.1 Linuxを理解するとは

- Linux世界とは
  - Linuxカーネルの作り出す世界
- Linuxカーネルとは
  - ファイルシステム・プロセス・ストリームを作り出すもの
  - システムコールを利用する

## 2.2 ライブラリ

- API（Application Programming Interface）
  - 何かを使ってプログラミングする時のインターフェース
    - C言語のライブラリのAPIは関数やマクロ
    - カーネルのAPIはシステムコール
    - 場合によっては設定ファイルやLinuxコマンドなどもAPIに入る

## 3.3 

- ストリーム
  - バイト列が流れる通り道
  - バイト列が流れとして考えられるものであればどんなものでもストリームにつなぐ事ができる
  - 使用例
    - ファイルへのアクセス
      - 事前にファイルにつながるストリームを作成するようシステムコールを使ってカーネルに依頼する
      - ストリームを操作してファイルにアクセス
    - SSDへのアクセス
    - HDDへのアクセス
    - キーボードへのアクセス
  - ストリームの両端がプロセスの場合
    - パイプと呼ばれる
      - grepやlessを使用するときに使われる
