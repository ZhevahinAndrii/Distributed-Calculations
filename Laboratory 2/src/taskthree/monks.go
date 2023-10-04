package main

import (
	"math"
	"math/rand"
	"time"
)

func createMonks(size int) chan int {
	energy := make(chan int, size)

	s := rand.NewSource(time.Now().Unix())
	random := rand.New(s)

	for i := 0; i < size; i++ {
		val := random.Intn(150) + 1
		print(val, " ")
		energy <- val
	}
	println()
	return energy
}

func fight(energyRead chan int, energyWrite chan int) {
	rival1 := <-energyRead
	rival2 := <-energyRead
	if rival1 > rival2 {
		println("Fight", rival1, "vs", rival2, ":", rival1, "wins")
		energyWrite <- rival1
	} else {
		println("Fight", rival1, "vs", rival2, ":", rival2, "wins")
		energyWrite <- rival2
	}
}

func main() {
	const size = 16
	energy := createMonks(size)

	rounds := int(math.Log2(size))
	fights := size / 2

	for i := 1; i <= rounds; i++ {
		nextMonk := make(chan int, fights)
		if i == rounds {
			go fight(energy, nextMonk)
			winner := <-nextMonk
			println("\nWinner of the fight is:", winner)
			break
		}

		for i := 0; i < fights; i++ {
			go fight(energy, nextMonk)
		}

		fights /= 2
		energy = nextMonk
	}
}
