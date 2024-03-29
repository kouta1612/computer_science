# protoファイルからコードを自動生成する

- [チュートリアル](https://zenn.dev/hsaki/books/golang-grpc-starting/viewer/codegenerate)
- [grpc公式ドキュメント](https://grpc.io/docs/languages/go/quickstart/)

## 環境構築

protocコマンドを実行できるようにするために、grpc公式ドキュメントを参考に下記を実行する必要がある

① Go plugins for the protocol compiler

```iterm
go install google.golang.org/protobuf/cmd/protoc-gen-go@v1.28
go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@v1.2
```

② Update your PATH so that the protoc compiler can find the plugins

```iterm
export PATH="$PATH:$(go env GOPATH)/bin"
```

## やったこと

Protocol Buffersのスキーマ定義からクライアントとサーバサイドのスキーマを生成し
それを満たす実装をクライアントとサーバで行い、gRPC通信をローカル上で試してみた。
チャプター6の「gRPCクライアントを動かしてみよう」までを一通りやってみたが、ストリームにいくつか種類があるみたいなので
興味が出てみたら挑戦してみる。
