package com.kita.store.modal

class Product {

    var id:Int
    var nama:String
    var harga:Int
    var deskripsi:String
    var photo:String

    constructor(id: Int, nama: String, harga: Int, deskripsi: String, photo: String) {
        this.id = id
        this.nama = nama
        this.harga = harga
        this.deskripsi = deskripsi
        this.photo = photo
    }
}