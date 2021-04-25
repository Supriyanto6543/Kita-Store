package com.kita.store.modal

class Orders {

    var id_user:String
    var jumlah:Int
    var catatan:String
    var nama_product:String

    constructor(id_user: String, jumlah: Int, catatan: String, nama_product: String) {
        this.id_user = id_user
        this.jumlah = jumlah
        this.catatan = catatan
        this.nama_product = nama_product
    }
}