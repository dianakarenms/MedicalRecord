package com.medicalrecord.utils

import android.app.Activity
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Constants {
    companion object {

        val BASE_VALUES = "base_values"
        val SOLUTION = "Solución"
        val ADDITIONAL_INFO = "Datos Adicionales"
        val DOCTOR_REFERENCE = "Referencia por Médico"

        var baseValues = linkedMapOf(
                "líquidos" to 150.0,
                "sol_fisiológica_" to 0.0,
                "naclhip_" to 0.0,
                "prot_10" to 3.5,
                "prot_8" to 0.0,
                "chs_50" to  8.5,
                "chs_10" to 0.0,
                "fosfato_k_" to 0.0,
                "kcl_amp_10_" to 3.0,
                "kcl_amp_5_" to 0.0,
                "lípidos" to 3.5,
                "calcio_" to 100.0,
                "magnesio_" to 40.0,
                "mvi_" to 3.0,
                "oligoelementos_" to 1.5,
                "carnitina_" to 1.5,
                "heparina_" to 0.5
        )
        /*
                false to "_108",
                false to "_5010",
                false to "_105",
                false to "_nacSol"
        */

        val displayNames = hashMapOf(
                "líquidos" to "líquidos",
                "sol_fisiológica_" to "sol. fisiológica al 0.9%",
                "prot_10" to "prot. 10%",
                "prot_8" to "prot. 8%", "chs_50" to "chs 50%",
                "chs_10" to "chs 10%",
                "kcl_amp_10_" to "cloruro de Potasio ámpula de 10 ml",
                "kcl_amp_5_" to "cloruro de Potasio ámpula de 5 ml",
                "naclhip_" to "concentrado de sódio",
                "lípidos" to "lípidos",
                "fosfato_k_" to "fosfato de potasio",
                "calcio_" to "calcio",
                "magnesio_" to "magnesio",
                "mvi_" to "multivitamínico intravenoso",
                "oligoelementos_" to "oligoelementos",
                "carnitina_" to "carnitina",
                "heparina_" to "heparina",
                "líquidos_tot" to "líquidos totales",
                "calorías_tot" to "calorías totales",
                "caljjml" to "calorías/ml",
                "caljjkgjjdia" to "calorías/Kg/día",
                "infusión" to "infusión",
                "nitrógeno" to "nitrógeno",
                "relcnpjntg" to "relcnp-ntg",
                "concentración" to "concentración",
                "gkm" to "gramos/Kg/minuto",
                "calprot" to "% calorías protéicas",
                "calgrasa" to "% calorías grasa",
                "calchs50" to "% calorías chs 50%",
                "calchs10" to "% calorías chs 10%",
                "líquidos_iv_tot" to "intravenosos totales",
                "sol_fisiológica" to "sol. fisiológica 0.9%",
                "trophamine_10" to "trophamine 10%",
                "trophamine_8" to "trophamine 8%",
                "intralipid_20" to "intralípidos 20%",
                "sg_50" to "sol. glucosada 50%",
                "sg_10" to "sol. glucosada 10%",
                "kcl_amp_10" to "cloruro de Potasio ámpula de 10 ml",
                "kcl_amp_5" to "cloruro de Potasio ámpula de 5 ml",
                "naclhip" to "concentrado de sódio",
                "fosfato_k" to "fosfáto de potasio",
                "glucca" to "gluconato de calcio/meq",
                "magnesio" to "magnesio/meq",
                "gluccaMl" to "gluconato de calcio/ml",
                "magnesioMl" to "magnesio/ml",
                "mvi" to "multivitamínico intravenoso",
                "oligoelementos" to "oligoelementos",
                "l_cisteína" to "l-cisteína",
                "carnitina" to "carnitina",
                "heparina" to "heparina",
                "abd" to "agua bidestilada"
        )

        val units = hashMapOf(
                "peso" to "Kg",
                "líquidos" to "ml/Kg/día",
                "sol_fisiológica_" to "meq/Kg/día",
                "prot_10" to "gr/Kg/día",
                "prot_8" to "gr/Kg/día",
                "chs_50" to "gr/Kg/día",
                "chs_10" to "gr/Kg/día",
                "kcl_amp_10_" to "meq/Kg/día",
                "kcl_amp_5_" to "meq/Kg/día",
                "naclhip_" to "meq/Kg/día",
                "lípidos" to "gr/Kg/día",
                "fosfato_k_" to "meq/Kg/día",
                "calcio_" to "mg/Kg/día",
                "magnesio_" to "mg/Kg/día",
                "mvi_" to "ml/día",
                "oligoelementos_" to "ml/Kg/día",
                "carnitina_" to "mg/Kg/día",
                "heparina_" to "Unidades",
                "líquidos_tot" to "ml/día",
                "calorías_tot" to "Kcal/día",
                "caljjml" to "kcal/ml",
                "caljjkgjjdia" to "Kcal/Kg/día",
                "infusión" to "ml/hr",
                "nitrógeno" to "",
                "relcnpjntg" to "",
                "concentración" to "%",
                "gkm" to "mg/Kg/min",
                "calprot" to "%",
                "calgrasa" to "%",
                "calchs50" to "%",
                "calchs10" to "%",
                "líquidos_iv_tot" to "ml/día",
                "sol_fisiológica" to "ml/día",
                "trophamine_10" to "ml/día",
                "trophamine_8" to "ml/día",
                "intralipid_20" to "ml/día",
                "sg_50" to "ml/día",
                "sg_10" to "ml/día",
                "kcl_amp_10" to "ml/día",
                "kcl_amp_5" to "ml/día",
                "naclhip" to "ml/día",
                "fosfato_k" to "ml/día",
                "glucca" to "meq/día",
                "magnesio" to "meq/día",
                "mvi" to "ml/día",
                "oligoelementos" to "ml/día",
                "l_cisteína" to "mg",
                "carnitina" to "mg",
                "heparina" to "Unidades",
                "abd" to "ml",
                "gluccaMl" to "ml/día",
                "magnesioMl" to "ml/día"
        )

        /**
         * Save and get HashMap in SharedPreference
         */
        fun saveHashMap(key: String, obj: Any, activity: Activity) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
            val editor = prefs.edit()
            val gson = Gson()
            val json = gson.toJson(obj)
            editor.putString(key, json)
            editor.apply()     // This line is IMPORTANT !!!
        }

        fun getHashMap(key: String, activity: Activity): LinkedHashMap<String, Double>? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
            val gson = Gson()
            val json = prefs.getString(key, "")
            val type = object : TypeToken<LinkedHashMap<String, Double>>() {

            }.type
            return if(json.isNotEmpty())
                gson.fromJson<LinkedHashMap<String, Double>>(json, type)
            else
                null
        }

        /*var solutionValuesKeys = listOf(
            "líquidos_iv_tot",
            "sol_fisiológica",
            "trophamine_10",
            "trophamine_8",
            "intralipid_20",
            "sg_50",
            "sg_10",
            "kcl_amp_10",
            "kcl_amp_5",
            "naclhip",
            "fosfato_k",
            "glucca",
            "magnesio",
            "mvi",
            "oligoelementos",
            "l_cisteína",
            "carnitina",
            "heparina",
            "abd")*/
    }
}