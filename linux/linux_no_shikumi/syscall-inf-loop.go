package main

import "os"

func main() {
	for {
		os.Getppid()
	}
}
