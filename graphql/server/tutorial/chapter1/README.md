# GraphQLサーバーを動かしてみる

- [チュートリアル](https://zenn.dev/hsaki/books/golang-graphql/viewer/tutorial)
- [gqlgen](https://github.com/99designs/gqlgen)

## 環境構築

参照元のチュートリアルだと、そのままgqlgenを入れようとしてもうまくいかなかったので
[gqlgen](https://github.com/99designs/gqlgen)を参考に、下記手順で行った。

① Initialise a new go module

```iterm
go mod init my_gql_server
```

② Add github.com/99designs/gqlgen to your project's tools.go

```iterm
printf '// +build tools\npackage tools\nimport (_"github.com/99designs/gqlgen"\n_ "github.com/99designs/gqlgen/graphql/introspection")' | gofmt > tools.go

go mod tidy
```

③ Initialise gqlgen config and generate models

```iterm
go run github.com/99designs/gqlgen init
```

④ Start the graphql server

```iterm
go run server.go
```
