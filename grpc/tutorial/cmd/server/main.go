package main

import (
	"context"
	"fmt"
	"log"
	"net"
	"os"
	"os/signal"

	mygrpc "mygrpc/pkg/grpc"

	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
)

type myServer struct {
	mygrpc.UnimplementedGreetingServiceServer
}

func NewServer() *myServer {
	return &myServer{}
}

func (s *myServer) Hello(ctx context.Context, req *mygrpc.HelloRequest) (*mygrpc.HelloResponse, error) {
	return &mygrpc.HelloResponse{
		Message: fmt.Sprintf("Hello, %s!", req.GetName()),
	}, nil
}

func main() {
	// 1. 8080番portのLisnterを作成
	port := 8080
	listener, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		panic(err)
	}

	// 2. gRPCサーバーを作成
	s := grpc.NewServer()

	mygrpc.RegisterGreetingServiceServer(s, NewServer())

	reflection.Register(s)

	// 3. 作成したgRPCサーバーを、8080番ポートで稼働させる
	go func() {
		log.Printf("start gRPC server port: %v", port)
		s.Serve(listener)
	}()

	// 4.Ctrl+Cが入力されたらGraceful shutdownされるようにする
	quit := make(chan os.Signal, 1)
	signal.Notify(quit, os.Interrupt)
	<-quit
	log.Println("stopping gRPC server...")
	s.GracefulStop()
}
