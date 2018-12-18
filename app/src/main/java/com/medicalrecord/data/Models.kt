package com.medicalrecord.data

class RefValue(
        var name: String,
        var value: Double,
        var type: String
)

// SOLUTIONS
class Solution(var id: Long?, // lista de valores de referencia con RefVal(name:String, value:Double)
               var líquidos_iv_tot: Double,
               var sol_fisiológica: Double,
               var trophamine_10: Double,
               var trophamine_8: Double,
               var intralipid_20: Double,
               var sg_50: Double,
               var sg_10: Double,
               var kcl_amp_10: Double,
               var kcl_amp_5: Double,
               var naclhip: Double,
               var fosfato_k: Double,
               var glucca: Double,
               var magnesio: Double,
               var mvi: Double,
               var oligoelementos: Double,
               var l_cisteína: Double,
               var carnitina: Double,
               var heparina: Double,
               var abd: Double
){
    constructor():this(null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0)
}

// ADITIONAL DATA
class AdditionalInfo(var id: Long?,
                     var líquidos_tot: Double,
                     var calorías_tot: Double,
                     var caljjml: Double,
                     var caljjkgjjdia: Double,
                     var infusión: Double,
                     var nitrógeno: Double,
                     var relcnpjntg: Double,
                     var concentración: Double,
                     var gkm: Double,
                     var calprot: Double,
                     var calgrasa: Double,
                     var calchs50: Double,
                     var calchs10: Double
){
    constructor():this(null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
}

// DOCTOR REFERENCE
class DoctorReference(var id: Long?,
                      var líquidos: Double,
                      var sol_fisiológica_: Double,
                      var naclhip_: Double,
                      var prot_10: Double,
                      var prot_8: Double,
                      var chs_50: Double,
                      var chs_10: Double,
                      var fosfato_k: Double,
                      var kcl_amp_10_: Double,
                      var kcl_amp_5_: Double,
                      var lípidos: Double,
                      var calcio_: Double,
                      var magnesio_: Double,
                      var mvi_: Double,
                      var oligoelementos_: Double,
                      var carnitina_: Double,
                      var heparina_: Double
){
    constructor():this(null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0)
}