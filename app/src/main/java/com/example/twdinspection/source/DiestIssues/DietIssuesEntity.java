package com.example.twdinspection.source.DiestIssues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class DietIssuesEntity {

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @SerializedName("mill_meaker_book_bal")
    @Expose
    private Integer millMeakerBookBal;
    @SerializedName("green_peas_ground_bal")
    @Expose
    private Integer greenPeasGroundBal;
    @SerializedName("rasampowder_book_bal")
    @Expose
    private Integer rasampowderBookBal;
    @SerializedName("pulihora_powder_book_bal")
    @Expose
    private Integer pulihoraPowderBookBal;
    @SerializedName("p._oil_ground_bal")
    @Expose
    private Integer pOilGroundBal;
    @SerializedName("red_gram_dall_book_bal")
    @Expose
    private Integer redGramDallBookBal;
    @SerializedName("vamu_ground_bal")
    @Expose
    private Integer vamuGroundBal;
    @SerializedName("moong_dal_book_bal")
    @Expose
    private Integer moongDalBookBal;
    @SerializedName("vamu_book_bal")
    @Expose
    private Integer vamuBookBal;
    @SerializedName("tea_powder_ground_bal")
    @Expose
    private Integer teaPowderGroundBal;
    @SerializedName("dry_coconut_powder_ground_bal")
    @Expose
    private Integer dryCoconutPowderGroundBal;
    @SerializedName("papaya_book_bal")
    @Expose
    private Integer papayaBookBal;
    @SerializedName("boost_ground_bal")
    @Expose
    private Integer boostGroundBal;
    @SerializedName("rajma_red_book_bal")
    @Expose
    private Integer rajmaRedBookBal;
    @SerializedName("ragi_vermicelli_book_bal")
    @Expose
    private Integer ragiVermicelliBookBal;
    @SerializedName("bengalgram_flour_ground_bal")
    @Expose
    private Integer bengalgramFlourGroundBal;
    @SerializedName("upma_mix_book_bal")
    @Expose
    private Integer upmaMixBookBal;
    @SerializedName("ragi_cookiees_book_bal")
    @Expose
    private Integer ragiCookieesBookBal;
    @SerializedName("alasandulu_book_bal")
    @Expose
    private Integer alasanduluBookBal;
    @SerializedName("all_ground_bal")
    @Expose
    private Integer allGroundBal;
    @SerializedName("papaya_ground_bal")
    @Expose
    private Integer papayaGroundBal;
    @SerializedName("chicken_book_bal")
    @Expose
    private Integer chickenBookBal;
    @SerializedName("banana_ground_bal")
    @Expose
    private Integer bananaGroundBal;
    @SerializedName("copra_book_bal")
    @Expose
    private Integer copraBookBal;
    @SerializedName("butter_milk_book_bal")
    @Expose
    private Integer butterMilkBookBal;
    @SerializedName("meary_gold/millet_book_bal")
    @Expose
    private Integer mearyGoldMilletBookBal;
    @SerializedName("papad_book_bal")
    @Expose
    private Integer papadBookBal;
    @SerializedName("chanadal_ground_bal")
    @Expose
    private Integer chanadalGroundBal;
    @SerializedName("butter_milk_ground_bal")
    @Expose
    private Integer butterMilkGroundBal;
    @SerializedName("zeera_ground_bal")
    @Expose
    private Integer zeeraGroundBal;
    @SerializedName("sweet_ground_bal")
    @Expose
    private Integer sweetGroundBal;
    @SerializedName("ghee_book_bal")
    @Expose
    private Integer gheeBookBal;
    @SerializedName("potatoes_book_bal")
    @Expose
    private Integer potatoesBookBal;
    @SerializedName("wheat_ground_bal")
    @Expose
    private Integer wheatGroundBal;
    @SerializedName("fish_ground_bal")
    @Expose
    private Integer fishGroundBal;
    @SerializedName("surf_book_bal")
    @Expose
    private Integer surfBookBal;
    @SerializedName("bagara_leaf_book_bal")
    @Expose
    private Integer bagaraLeafBookBal;
    @SerializedName("idly_mix_(weekly_twice)_book_bal")
    @Expose
    private Integer idlyMixWeeklyTwiceBookBal;
    @SerializedName("rice_ground_bal")
    @Expose
    private Integer riceGroundBal;
    @SerializedName("garlic_book_bal")
    @Expose
    private Integer garlicBookBal;
    @SerializedName("sajeera_book_bal")
    @Expose
    private Integer sajeeraBookBal;
    @SerializedName("corriander_ground_bal")
    @Expose
    private Integer corrianderGroundBal;
    @SerializedName("handwash_liquid_ground_bal")
    @Expose
    private Integer handwashLiquidGroundBal;
    @SerializedName("bread_ground_bal")
    @Expose
    private Integer breadGroundBal;
    @SerializedName("vermicelli_ground_bal")
    @Expose
    private Integer vermicelliGroundBal;
    @SerializedName("kismis_book_bal")
    @Expose
    private Integer kismisBookBal;
    @SerializedName("gulam_jam_powder_ground_bal")
    @Expose
    private Integer gulamJamPowderGroundBal;
    @SerializedName("sambar_powder_ground_bal")
    @Expose
    private Integer sambarPowderGroundBal;
    @SerializedName("noodles_book_bal")
    @Expose
    private Integer noodlesBookBal;
    @SerializedName("garlic_ground_bal")
    @Expose
    private Integer garlicGroundBal;
    @SerializedName("till_ground_bal")
    @Expose
    private Integer tillGroundBal;
    @SerializedName("dalda_book_bal")
    @Expose
    private Integer daldaBookBal;
    @SerializedName("dry_coconut_powder_book_bal")
    @Expose
    private Integer dryCoconutPowderBookBal;
    @SerializedName("muramuralu_book_bal")
    @Expose
    private Integer muramuraluBookBal;
    @SerializedName("cabbage_ground_bal")
    @Expose
    private Integer cabbageGroundBal;
    @SerializedName("dhania_book_bal")
    @Expose
    private Integer dhaniaBookBal;
    @SerializedName("dry_coconut_book_bal")
    @Expose
    private Integer dryCoconutBookBal;
    @SerializedName("sweet_potato_book_bal")
    @Expose
    private Integer sweetPotatoBookBal;
    @SerializedName("wheat_ravva_book_bal")
    @Expose
    private Integer wheatRavvaBookBal;
    @SerializedName("mill_meaker_ground_bal")
    @Expose
    private Integer millMeakerGroundBal;
    @SerializedName("gasalu_ground_bal")
    @Expose
    private Integer gasaluGroundBal;
    @SerializedName("ragi_vermicelli_ground_bal")
    @Expose
    private Integer ragiVermicelliGroundBal;
    @SerializedName("spl_fruit_book_bal")
    @Expose
    private Integer splFruitBookBal;
    @SerializedName("fire_wood_book_bal")
    @Expose
    private Integer fireWoodBookBal;
    @SerializedName("bg_atta_ground_bal")
    @Expose
    private Integer bgAttaGroundBal;
    @SerializedName("sugar_ground_bal")
    @Expose
    private Integer sugarGroundBal;
    @SerializedName("ap_snak_food_book_bal")
    @Expose
    private Integer apSnakFoodBookBal;
    @SerializedName("bengal_gram_roast_ground_bal")
    @Expose
    private Integer bengalGramRoastGroundBal;
    @SerializedName("upma_ravva_book_bal")
    @Expose
    private Integer upmaRavvaBookBal;
    @SerializedName("ground_nuts_book_bal")
    @Expose
    private Integer groundNutsBookBal;
    @SerializedName("red_chilli_powder_book_bal")
    @Expose
    private Integer redChilliPowderBookBal;
    @SerializedName("milk_bread_book_bal")
    @Expose
    private Integer milkBreadBookBal;
    @SerializedName("curd_ground_bal")
    @Expose
    private Integer curdGroundBal;
    @SerializedName("populu_book_bal")
    @Expose
    private Integer populuBookBal;
    @SerializedName("eggs_ground_bal")
    @Expose
    private Integer eggsGroundBal;
    @SerializedName("mustard_book_bal")
    @Expose
    private Integer mustardBookBal;
    @SerializedName("curd_book_bal")
    @Expose
    private Integer curdBookBal;
    @SerializedName("ginger_book_bal")
    @Expose
    private Integer gingerBookBal;
    @SerializedName("dalchana_ground_bal")
    @Expose
    private Integer dalchanaGroundBal;
    @SerializedName("baking_powder_book_bal")
    @Expose
    private Integer bakingPowderBookBal;
    @SerializedName("sambar_powder_book_bal")
    @Expose
    private Integer sambarPowderBookBal;
    @SerializedName("wheat_flour_ground_bal")
    @Expose
    private Integer wheatFlourGroundBal;
    @SerializedName("surf_ground_bal")
    @Expose
    private Integer surfGroundBal;
    @SerializedName("fire_wood_ground_bal")
    @Expose
    private Integer fireWoodGroundBal;
    @SerializedName("upma_mix_ground_bal")
    @Expose
    private Integer upmaMixGroundBal;
    @SerializedName("dhania_ground_bal")
    @Expose
    private Integer dhaniaGroundBal;
    @SerializedName("mustard_ground_bal")
    @Expose
    private Integer mustardGroundBal;
    @SerializedName("condiments_ground_bal")
    @Expose
    private Integer condimentsGroundBal;
    @SerializedName("rajma_red_ground_bal")
    @Expose
    private Integer rajmaRedGroundBal;
    @SerializedName("sweet_oil_book_bal")
    @Expose
    private Integer sweetOilBookBal;
    @SerializedName("muramuralu_ground_bal")
    @Expose
    private Integer muramuraluGroundBal;
    @SerializedName("salt_book_bal")
    @Expose
    private Integer saltBookBal;
    @SerializedName("biscuits_book_bal")
    @Expose
    private Integer biscuitsBookBal;
    @SerializedName("putnalu_ground_bal")
    @Expose
    private Integer putnaluGroundBal;
    @SerializedName("batana_book_bal")
    @Expose
    private Integer batanaBookBal;
    @SerializedName("peas_book_bal")
    @Expose
    private Integer peasBookBal;
    @SerializedName("dhanniya_powder_book_bal")
    @Expose
    private Integer dhanniyaPowderBookBal;
    @SerializedName("red_chillies_ground_bal")
    @Expose
    private Double redChilliesGroundBal;
    @SerializedName("rai_book_bal")
    @Expose
    private Integer raiBookBal;
    @SerializedName("transport_book_bal")
    @Expose
    private Integer transportBookBal;
    @SerializedName("vegetables_book_bal")
    @Expose
    private Integer vegetablesBookBal;
    @SerializedName("zohar_vermiselly_ground_bal")
    @Expose
    private Integer zoharVermisellyGroundBal;
    @SerializedName("lemon_book_bal")
    @Expose
    private Integer lemonBookBal;
    @SerializedName("lavanga_book_bal")
    @Expose
    private Integer lavangaBookBal;
    @SerializedName("blackgram_dal_ground_bal")
    @Expose
    private Integer blackgramDalGroundBal;
    @SerializedName("eggs_book_bal")
    @Expose
    private Integer eggsBookBal;
    @SerializedName("bg_atta_book_bal")
    @Expose
    private Integer bgAttaBookBal;
    @SerializedName("chanadal_book_bal")
    @Expose
    private Integer chanadalBookBal;
    @SerializedName("upma_ravva_ground_bal")
    @Expose
    private Integer upmaRavvaGroundBal;
    @SerializedName("mutton_ground_bal")
    @Expose
    private Integer muttonGroundBal;
    @SerializedName("khaju_book_bal")
    @Expose
    private Integer khajuBookBal;
    @SerializedName("milk_book_bal")
    @Expose
    private Integer milkBookBal;
    @SerializedName("pesarlu_book_bal")
    @Expose
    private Integer pesarluBookBal;
    @SerializedName("onions_book_bal")
    @Expose
    private Integer onionsBookBal;
    @SerializedName("pepper_ground_bal")
    @Expose
    private Integer pepperGroundBal;
    @SerializedName("zohar_cookiees_book_bal")
    @Expose
    private Integer zoharCookieesBookBal;
    @SerializedName("groundnut/palli_patti_ground_bal")
    @Expose
    private Integer groundnutPalliPattiGroundBal;
    @SerializedName("bazra_cookiees_ground_bal")
    @Expose
    private Integer bazraCookieesGroundBal;
    @SerializedName("rai_ground_bal")
    @Expose
    private Integer raiGroundBal;
    @SerializedName("yellow_gram_dal_book_bal")
    @Expose
    private Integer yellowGramDalBookBal;
    @SerializedName("raitha_book_bal")
    @Expose
    private Integer raithaBookBal;
    @SerializedName("boiled_pears_with_onions_ground_bal")
    @Expose
    private Integer boiledPearsWithOnionsGroundBal;
    @SerializedName("all_book_bal")
    @Expose
    private Integer allBookBal;
    @SerializedName("bengal_gram_roast_book_bal")
    @Expose
    private Integer bengalGramRoastBookBal;
    @SerializedName("beens_ground_bal")
    @Expose
    private Integer beensGroundBal;
    @SerializedName("jowar_ground_bal")
    @Expose
    private Integer jowarGroundBal;
    @SerializedName("onions_ground_bal")
    @Expose
    private Integer onionsGroundBal;
    @SerializedName("idly_rava_book_bal")
    @Expose
    private Integer idlyRavaBookBal;
    @SerializedName("milk_bread_ground_bal")
    @Expose
    private Integer milkBreadGroundBal;
    @SerializedName("garlic_jinger_paste_ground_bal")
    @Expose
    private Integer garlicJingerPasteGroundBal;
    @SerializedName("bazra_cookiees_book_bal")
    @Expose
    private Integer bazraCookieesBookBal;
    @SerializedName("bansi_ravva_ground_bal")
    @Expose
    private Integer bansiRavvaGroundBal;
    @SerializedName("doodu_atukulu_ground_bal")
    @Expose
    private Integer dooduAtukuluGroundBal;
    @SerializedName("rack_salt_book_bal")
    @Expose
    private Integer rackSaltBookBal;
    @SerializedName("baking_powder_ground_bal")
    @Expose
    private Integer bakingPowderGroundBal;
    @SerializedName("red_chilli_powder_ground_bal")
    @Expose
    private Integer redChilliPowderGroundBal;
    @SerializedName("sweet_porridge_ground_bal")
    @Expose
    private Integer sweetPorridgeGroundBal;
    @SerializedName("ragimalt_ground_bal")
    @Expose
    private Integer ragimaltGroundBal;
    @SerializedName("pulihora_powder_ground_bal")
    @Expose
    private Integer pulihoraPowderGroundBal;
    @SerializedName("biscuits_ground_bal")
    @Expose
    private Integer biscuitsGroundBal;
    @SerializedName("fish_book_bal")
    @Expose
    private Integer fishBookBal;
    @SerializedName("turmeric_powder_book_bal")
    @Expose
    private Double turmericPowderBookBal;
    @SerializedName("blackgram_dal_book_bal")
    @Expose
    private Integer blackgramDalBookBal;
    @SerializedName("khaju_ground_bal")
    @Expose
    private Integer khajuGroundBal;
    @SerializedName("milk_powder_book_bal")
    @Expose
    private Integer milkPowderBookBal;
    @SerializedName("lavanga_ground_bal")
    @Expose
    private Integer lavangaGroundBal;
    @SerializedName("atukulu_book_bal")
    @Expose
    private Integer atukuluBookBal;
    @SerializedName("chicken_ground_bal")
    @Expose
    private Integer chickenGroundBal;
    @SerializedName("lemon_ground_bal")
    @Expose
    private Integer lemonGroundBal;
    @SerializedName("sweet_potato_ground_bal")
    @Expose
    private Integer sweetPotatoGroundBal;
    @SerializedName("dry_coconut_ground_bal")
    @Expose
    private Integer dryCoconutGroundBal;
    @SerializedName("garlic_jinger_paste_book_bal")
    @Expose
    private Integer garlicJingerPasteBookBal;
    @SerializedName("saggu_rice_book_bal")
    @Expose
    private Integer sagguRiceBookBal;
    @SerializedName("atukulu_ground_bal")
    @Expose
    private Integer atukuluGroundBal;
    @SerializedName("milk_powder_ground_bal")
    @Expose
    private Integer milkPowderGroundBal;
    @SerializedName("populu_ground_bal")
    @Expose
    private Integer populuGroundBal;
    @SerializedName("pickle_ground_bal")
    @Expose
    private Integer pickleGroundBal;
    @SerializedName("udath_dall_ground_bal")
    @Expose
    private Integer udathDallGroundBal;
    @SerializedName("moong_dal_ground_bal")
    @Expose
    private Integer moongDalGroundBal;
    @SerializedName("lemon_salt_ground_bal")
    @Expose
    private Integer lemonSaltGroundBal;
    @SerializedName("bombai_rava_book_bal")
    @Expose
    private Integer bombaiRavaBookBal;
    @SerializedName("butter_ground_bal")
    @Expose
    private Integer butterGroundBal;
    @SerializedName("transport_ground_bal")
    @Expose
    private Integer transportGroundBal;
    @SerializedName("custer_poweder_book_bal")
    @Expose
    private Integer custerPowederBookBal;
    @SerializedName("boost_book_bal")
    @Expose
    private Integer boostBookBal;
    @SerializedName("sweet_porridge_book_bal")
    @Expose
    private Integer sweetPorridgeBookBal;
    @SerializedName("pulka_book_bal")
    @Expose
    private Integer pulkaBookBal;
    @SerializedName("jaggery_ground_bal")
    @Expose
    private Integer jaggeryGroundBal;
    @SerializedName("bengalgram_flour_book_bal")
    @Expose
    private Integer bengalgramFlourBookBal;
    @SerializedName("bengal_gram_dal_book_bal")
    @Expose
    private Integer bengalGramDalBookBal;
    @SerializedName("soan_papidi_ground_bal")
    @Expose
    private Integer soanPapidiGroundBal;
    @SerializedName("kichidi_mix_ground_bal")
    @Expose
    private Integer kichidiMixGroundBal;
    @SerializedName("ilachi_ground_bal")
    @Expose
    private Integer ilachiGroundBal;
    @SerializedName("masala_book_bal")
    @Expose
    private Integer masalaBookBal;
    @SerializedName("dhanniya_powder_ground_bal")
    @Expose
    private Integer dhanniyaPowderGroundBal;
    @SerializedName("milk_ground_bal")
    @Expose
    private Integer milkGroundBal;
    @SerializedName("zohar_cookiees_ground_bal")
    @Expose
    private Integer zoharCookieesGroundBal;
    @SerializedName("sajeera_ground_bal")
    @Expose
    private Integer sajeeraGroundBal;
    @SerializedName("butter_book_bal")
    @Expose
    private Integer butterBookBal;
    @SerializedName("handwash_liquid_book_bal")
    @Expose
    private Integer handwashLiquidBookBal;
    @SerializedName("beens_book_bal")
    @Expose
    private Integer beensBookBal;
    @SerializedName("jowar_book_bal")
    @Expose
    private Integer jowarBookBal;
    @SerializedName("pickle_book_bal")
    @Expose
    private Integer pickleBookBal;
    @SerializedName("gas_ground_bal")
    @Expose
    private Integer gasGroundBal;
    @SerializedName("ragimalt_book_bal")
    @Expose
    private Integer ragimaltBookBal;
    @SerializedName("menthulu_book_bal")
    @Expose
    private Integer menthuluBookBal;
    @SerializedName("red_gram_dall_ground_bal")
    @Expose
    private Integer redGramDallGroundBal;
    @SerializedName("batana_ground_bal")
    @Expose
    private Integer batanaGroundBal;
    @SerializedName("custer_poweder_ground_bal")
    @Expose
    private Integer custerPowederGroundBal;
    @SerializedName("bengal_gram_dal_ground_bal")
    @Expose
    private Integer bengalGramDalGroundBal;
    @SerializedName("vermicelli_book_bal")
    @Expose
    private Integer vermicelliBookBal;
    @SerializedName("bajra_ground_bal")
    @Expose
    private Integer bajraGroundBal;
    @SerializedName("ragi_mixer_ground_bal")
    @Expose
    private Integer ragiMixerGroundBal;
    @SerializedName("tamarind_ground_bal")
    @Expose
    private Integer tamarindGroundBal;
    @SerializedName("bobbarlu_ground_bal")
    @Expose
    private Integer bobbarluGroundBal;
    @SerializedName("peas_ground_bal")
    @Expose
    private Integer peasGroundBal;
    @SerializedName("ap_snak_food_ground_bal")
    @Expose
    private Integer apSnakFoodGroundBal;
    @SerializedName("gulam_jam_powder_book_bal")
    @Expose
    private Integer gulamJamPowderBookBal;
    @SerializedName("vegetables_ground_bal")
    @Expose
    private Integer vegetablesGroundBal;
    @SerializedName("till_book_bal")
    @Expose
    private Integer tillBookBal;
    @SerializedName("sweet_oil_ground_bal")
    @Expose
    private Integer sweetOilGroundBal;
    @SerializedName("tea_powder_book_bal")
    @Expose
    private Integer teaPowderBookBal;
    @SerializedName("bombai_rava_ground_bal")
    @Expose
    private Integer bombaiRavaGroundBal;
    @SerializedName("zeera_book_bal")
    @Expose
    private Integer zeeraBookBal;
    @SerializedName("dalchana_book_bal")
    @Expose
    private Integer dalchanaBookBal;
    @SerializedName("sanagalu_ground_bal")
    @Expose
    private Integer sanagaluGroundBal;
    @SerializedName("noodles_ground_bal")
    @Expose
    private Integer noodlesGroundBal;
    @SerializedName("putnalu_book_bal")
    @Expose
    private Integer putnaluBookBal;
    @SerializedName("alasandulu_ground_bal")
    @Expose
    private Integer alasanduluGroundBal;
    @SerializedName("bansi_ravva_book_bal")
    @Expose
    private Integer bansiRavvaBookBal;
    @SerializedName("condiments_book_bal")
    @Expose
    private Integer condimentsBookBal;
    @SerializedName("bobbarlu_book_bal")
    @Expose
    private Integer bobbarluBookBal;
    @SerializedName("gasalu_book_bal")
    @Expose
    private Integer gasaluBookBal;
    @SerializedName("pulka_ground_bal")
    @Expose
    private Integer pulkaGroundBal;
    @SerializedName("idly_mix_(weekly_twice)_ground_bal")
    @Expose
    private Integer idlyMixWeeklyTwiceGroundBal;
    @SerializedName("sanagalu_book_bal")
    @Expose
    private Integer sanagaluBookBal;
    @SerializedName("mutton_book_bal")
    @Expose
    private Integer muttonBookBal;
    @SerializedName("rasampowder_ground_bal")
    @Expose
    private Integer rasampowderGroundBal;
    @SerializedName("ginger_ground_bal")
    @Expose
    private Integer gingerGroundBal;
    @SerializedName("groundnut/palli_patti_book_bal")
    @Expose
    private Integer groundnutPalliPattiBookBal;
    @SerializedName("bagara_leaf_ground_bal")
    @Expose
    private Integer bagaraLeafGroundBal;
    @SerializedName("ghee_ground_bal")
    @Expose
    private Integer gheeGroundBal;
    @SerializedName("pepper_book_bal")
    @Expose
    private Integer pepperBookBal;
    @SerializedName("sweet_book_bal")
    @Expose
    private Integer sweetBookBal;
    @SerializedName("p._oil_book_bal")
    @Expose
    private Integer pOilBookBal;
    @SerializedName("corriander_book_bal")
    @Expose
    private Integer corrianderBookBal;
    @SerializedName("salt_ground_bal")
    @Expose
    private Integer saltGroundBal;
    @SerializedName("green_peas_book_bal")
    @Expose
    private Integer greenPeasBookBal;
    @SerializedName("ladys_finger_book_bal")
    @Expose
    private Integer ladysFingerBookBal;
    @SerializedName("pesarlu_ground_bal")
    @Expose
    private Integer pesarluGroundBal;
    @SerializedName("rack_salt_ground_bal")
    @Expose
    private Integer rackSaltGroundBal;
    @SerializedName("kichidi_mix_book_bal")
    @Expose
    private Integer kichidiMixBookBal;
    @SerializedName("banana_book_bal")
    @Expose
    private Integer bananaBookBal;
    @SerializedName("masala_ground_bal")
    @Expose
    private Integer masalaGroundBal;
    @SerializedName("papad_ground_bal")
    @Expose
    private Integer papadGroundBal;
    @SerializedName("meary_gold/millet_ground_bal")
    @Expose
    private Integer mearyGoldMilletGroundBal;
    @SerializedName("udath_dall_book_bal")
    @Expose
    private Integer udathDallBookBal;
    @SerializedName("wheat_book_bal")
    @Expose
    private Integer wheatBookBal;
    @SerializedName("dalda_ground_bal")
    @Expose
    private Integer daldaGroundBal;
    @SerializedName("tamarind_book_bal")
    @Expose
    private Integer tamarindBookBal;
    @SerializedName("saggu_rice_ground_bal")
    @Expose
    private Integer sagguRiceGroundBal;
    @SerializedName("ragi_mixer_book_bal")
    @Expose
    private Integer ragiMixerBookBal;
    @SerializedName("boiled_pears_with_onions_book_bal")
    @Expose
    private Integer boiledPearsWithOnionsBookBal;
    @SerializedName("kismis_ground_bal")
    @Expose
    private Integer kismisGroundBal;
    @SerializedName("zohar_vermiselly_book_bal")
    @Expose
    private Integer zoharVermisellyBookBal;
    @SerializedName("bread_book_bal")
    @Expose
    private Integer breadBookBal;
    @SerializedName("soan_papidi_book_bal")
    @Expose
    private Integer soanPapidiBookBal;
    @SerializedName("ground_nuts_ground_bal")
    @Expose
    private Integer groundNutsGroundBal;
    @SerializedName("yellow_gram_dal_ground_bal")
    @Expose
    private Integer yellowGramDalGroundBal;
    @SerializedName("ladys_finger_ground_bal")
    @Expose
    private Integer ladysFingerGroundBal;
    @SerializedName("g.g.dal_book_bal")
    @Expose
    private Integer gGDalBookBal;
    @SerializedName("cabbage_book_bal")
    @Expose
    private Integer cabbageBookBal;
    @SerializedName("menthulu_ground_bal")
    @Expose
    private Integer menthuluGroundBal;
    @SerializedName("turmeric_powder_ground_bal")
    @Expose
    private Double turmericPowderGroundBal;
    @SerializedName("lemon_salt_book_bal")
    @Expose
    private Integer lemonSaltBookBal;
    @SerializedName("raitha_ground_bal")
    @Expose
    private Integer raithaGroundBal;
    @SerializedName("wheat_ravva_ground_bal")
    @Expose
    private Integer wheatRavvaGroundBal;
    @SerializedName("doodu_atukulu_book_bal")
    @Expose
    private Integer dooduAtukuluBookBal;
    @SerializedName("baadam_book_bal")
    @Expose
    private Integer baadamBookBal;
    @SerializedName("wheat_flour_book_bal")
    @Expose
    private Integer wheatFlourBookBal;
    @SerializedName("sugar_book_bal")
    @Expose
    private Integer sugarBookBal;
    @SerializedName("idly_rava_ground_bal")
    @Expose
    private Integer idlyRavaGroundBal;
    @SerializedName("g.g.dal_ground_bal")
    @Expose
    private Integer gGDalGroundBal;
    @SerializedName("bagara_flower_book_bal")
    @Expose
    private Integer bagaraFlowerBookBal;
    @SerializedName("copra_ground_bal")
    @Expose
    private Integer copraGroundBal;
    @SerializedName("spl_fruit_ground_bal")
    @Expose
    private Integer splFruitGroundBal;
    @SerializedName("rice_book_bal")
    @Expose
    private Integer riceBookBal;
    @SerializedName("potatoes_ground_bal")
    @Expose
    private Integer potatoesGroundBal;
    @SerializedName("ilachi_book_bal")
    @Expose
    private Integer ilachiBookBal;
    @SerializedName("red_chillies_book_bal")
    @Expose
    private Double redChilliesBookBal;
    @SerializedName("jaggery_book_bal")
    @Expose
    private Integer jaggeryBookBal;
    @SerializedName("bagara_flower_ground_bal")
    @Expose
    private Integer bagaraFlowerGroundBal;
    @SerializedName("ragi_cookiees_ground_bal")
    @Expose
    private Integer ragiCookieesGroundBal;
    @SerializedName("gas_book_bal")
    @Expose
    private Integer gasBookBal;
    @SerializedName("bajra_book_bal")
    @Expose
    private Integer bajraBookBal;
    @SerializedName("baadam_ground_bal")
    @Expose
    private Integer baadamGroundBal;

    public Integer getMillMeakerBookBal() {
        return millMeakerBookBal;
    }

    public void setMillMeakerBookBal(Integer millMeakerBookBal) {
        this.millMeakerBookBal = millMeakerBookBal;
    }

    public Integer getGreenPeasGroundBal() {
        return greenPeasGroundBal;
    }

    public void setGreenPeasGroundBal(Integer greenPeasGroundBal) {
        this.greenPeasGroundBal = greenPeasGroundBal;
    }

    public Integer getRasampowderBookBal() {
        return rasampowderBookBal;
    }

    public void setRasampowderBookBal(Integer rasampowderBookBal) {
        this.rasampowderBookBal = rasampowderBookBal;
    }

    public Integer getPulihoraPowderBookBal() {
        return pulihoraPowderBookBal;
    }

    public void setPulihoraPowderBookBal(Integer pulihoraPowderBookBal) {
        this.pulihoraPowderBookBal = pulihoraPowderBookBal;
    }

    public Integer getPOilGroundBal() {
        return pOilGroundBal;
    }

    public void setPOilGroundBal(Integer pOilGroundBal) {
        this.pOilGroundBal = pOilGroundBal;
    }

    public Integer getRedGramDallBookBal() {
        return redGramDallBookBal;
    }

    public void setRedGramDallBookBal(Integer redGramDallBookBal) {
        this.redGramDallBookBal = redGramDallBookBal;
    }

    public Integer getVamuGroundBal() {
        return vamuGroundBal;
    }

    public void setVamuGroundBal(Integer vamuGroundBal) {
        this.vamuGroundBal = vamuGroundBal;
    }

    public Integer getMoongDalBookBal() {
        return moongDalBookBal;
    }

    public void setMoongDalBookBal(Integer moongDalBookBal) {
        this.moongDalBookBal = moongDalBookBal;
    }

    public Integer getVamuBookBal() {
        return vamuBookBal;
    }

    public void setVamuBookBal(Integer vamuBookBal) {
        this.vamuBookBal = vamuBookBal;
    }

    public Integer getTeaPowderGroundBal() {
        return teaPowderGroundBal;
    }

    public void setTeaPowderGroundBal(Integer teaPowderGroundBal) {
        this.teaPowderGroundBal = teaPowderGroundBal;
    }

    public Integer getDryCoconutPowderGroundBal() {
        return dryCoconutPowderGroundBal;
    }

    public void setDryCoconutPowderGroundBal(Integer dryCoconutPowderGroundBal) {
        this.dryCoconutPowderGroundBal = dryCoconutPowderGroundBal;
    }

    public Integer getPapayaBookBal() {
        return papayaBookBal;
    }

    public void setPapayaBookBal(Integer papayaBookBal) {
        this.papayaBookBal = papayaBookBal;
    }

    public Integer getBoostGroundBal() {
        return boostGroundBal;
    }

    public void setBoostGroundBal(Integer boostGroundBal) {
        this.boostGroundBal = boostGroundBal;
    }

    public Integer getRajmaRedBookBal() {
        return rajmaRedBookBal;
    }

    public void setRajmaRedBookBal(Integer rajmaRedBookBal) {
        this.rajmaRedBookBal = rajmaRedBookBal;
    }

    public Integer getRagiVermicelliBookBal() {
        return ragiVermicelliBookBal;
    }

    public void setRagiVermicelliBookBal(Integer ragiVermicelliBookBal) {
        this.ragiVermicelliBookBal = ragiVermicelliBookBal;
    }

    public Integer getBengalgramFlourGroundBal() {
        return bengalgramFlourGroundBal;
    }

    public void setBengalgramFlourGroundBal(Integer bengalgramFlourGroundBal) {
        this.bengalgramFlourGroundBal = bengalgramFlourGroundBal;
    }

    public Integer getUpmaMixBookBal() {
        return upmaMixBookBal;
    }

    public void setUpmaMixBookBal(Integer upmaMixBookBal) {
        this.upmaMixBookBal = upmaMixBookBal;
    }

    public Integer getRagiCookieesBookBal() {
        return ragiCookieesBookBal;
    }

    public void setRagiCookieesBookBal(Integer ragiCookieesBookBal) {
        this.ragiCookieesBookBal = ragiCookieesBookBal;
    }

    public Integer getAlasanduluBookBal() {
        return alasanduluBookBal;
    }

    public void setAlasanduluBookBal(Integer alasanduluBookBal) {
        this.alasanduluBookBal = alasanduluBookBal;
    }

    public Integer getAllGroundBal() {
        return allGroundBal;
    }

    public void setAllGroundBal(Integer allGroundBal) {
        this.allGroundBal = allGroundBal;
    }

    public Integer getPapayaGroundBal() {
        return papayaGroundBal;
    }

    public void setPapayaGroundBal(Integer papayaGroundBal) {
        this.papayaGroundBal = papayaGroundBal;
    }

    public Integer getChickenBookBal() {
        return chickenBookBal;
    }

    public void setChickenBookBal(Integer chickenBookBal) {
        this.chickenBookBal = chickenBookBal;
    }

    public Integer getBananaGroundBal() {
        return bananaGroundBal;
    }

    public void setBananaGroundBal(Integer bananaGroundBal) {
        this.bananaGroundBal = bananaGroundBal;
    }

    public Integer getCopraBookBal() {
        return copraBookBal;
    }

    public void setCopraBookBal(Integer copraBookBal) {
        this.copraBookBal = copraBookBal;
    }

    public Integer getButterMilkBookBal() {
        return butterMilkBookBal;
    }

    public void setButterMilkBookBal(Integer butterMilkBookBal) {
        this.butterMilkBookBal = butterMilkBookBal;
    }

    public Integer getMearyGoldMilletBookBal() {
        return mearyGoldMilletBookBal;
    }

    public void setMearyGoldMilletBookBal(Integer mearyGoldMilletBookBal) {
        this.mearyGoldMilletBookBal = mearyGoldMilletBookBal;
    }

    public Integer getPapadBookBal() {
        return papadBookBal;
    }

    public void setPapadBookBal(Integer papadBookBal) {
        this.papadBookBal = papadBookBal;
    }

    public Integer getChanadalGroundBal() {
        return chanadalGroundBal;
    }

    public void setChanadalGroundBal(Integer chanadalGroundBal) {
        this.chanadalGroundBal = chanadalGroundBal;
    }

    public Integer getButterMilkGroundBal() {
        return butterMilkGroundBal;
    }

    public void setButterMilkGroundBal(Integer butterMilkGroundBal) {
        this.butterMilkGroundBal = butterMilkGroundBal;
    }

    public Integer getZeeraGroundBal() {
        return zeeraGroundBal;
    }

    public void setZeeraGroundBal(Integer zeeraGroundBal) {
        this.zeeraGroundBal = zeeraGroundBal;
    }

    public Integer getSweetGroundBal() {
        return sweetGroundBal;
    }

    public void setSweetGroundBal(Integer sweetGroundBal) {
        this.sweetGroundBal = sweetGroundBal;
    }

    public Integer getGheeBookBal() {
        return gheeBookBal;
    }

    public void setGheeBookBal(Integer gheeBookBal) {
        this.gheeBookBal = gheeBookBal;
    }

    public Integer getPotatoesBookBal() {
        return potatoesBookBal;
    }

    public void setPotatoesBookBal(Integer potatoesBookBal) {
        this.potatoesBookBal = potatoesBookBal;
    }

    public Integer getWheatGroundBal() {
        return wheatGroundBal;
    }

    public void setWheatGroundBal(Integer wheatGroundBal) {
        this.wheatGroundBal = wheatGroundBal;
    }

    public Integer getFishGroundBal() {
        return fishGroundBal;
    }

    public void setFishGroundBal(Integer fishGroundBal) {
        this.fishGroundBal = fishGroundBal;
    }

    public Integer getSurfBookBal() {
        return surfBookBal;
    }

    public void setSurfBookBal(Integer surfBookBal) {
        this.surfBookBal = surfBookBal;
    }

    public Integer getBagaraLeafBookBal() {
        return bagaraLeafBookBal;
    }

    public void setBagaraLeafBookBal(Integer bagaraLeafBookBal) {
        this.bagaraLeafBookBal = bagaraLeafBookBal;
    }

    public Integer getIdlyMixWeeklyTwiceBookBal() {
        return idlyMixWeeklyTwiceBookBal;
    }

    public void setIdlyMixWeeklyTwiceBookBal(Integer idlyMixWeeklyTwiceBookBal) {
        this.idlyMixWeeklyTwiceBookBal = idlyMixWeeklyTwiceBookBal;
    }

    public Integer getRiceGroundBal() {
        return riceGroundBal;
    }

    public void setRiceGroundBal(Integer riceGroundBal) {
        this.riceGroundBal = riceGroundBal;
    }

    public Integer getGarlicBookBal() {
        return garlicBookBal;
    }

    public void setGarlicBookBal(Integer garlicBookBal) {
        this.garlicBookBal = garlicBookBal;
    }

    public Integer getSajeeraBookBal() {
        return sajeeraBookBal;
    }

    public void setSajeeraBookBal(Integer sajeeraBookBal) {
        this.sajeeraBookBal = sajeeraBookBal;
    }

    public Integer getCorrianderGroundBal() {
        return corrianderGroundBal;
    }

    public void setCorrianderGroundBal(Integer corrianderGroundBal) {
        this.corrianderGroundBal = corrianderGroundBal;
    }

    public Integer getHandwashLiquidGroundBal() {
        return handwashLiquidGroundBal;
    }

    public void setHandwashLiquidGroundBal(Integer handwashLiquidGroundBal) {
        this.handwashLiquidGroundBal = handwashLiquidGroundBal;
    }

    public Integer getBreadGroundBal() {
        return breadGroundBal;
    }

    public void setBreadGroundBal(Integer breadGroundBal) {
        this.breadGroundBal = breadGroundBal;
    }

    public Integer getVermicelliGroundBal() {
        return vermicelliGroundBal;
    }

    public void setVermicelliGroundBal(Integer vermicelliGroundBal) {
        this.vermicelliGroundBal = vermicelliGroundBal;
    }

    public Integer getKismisBookBal() {
        return kismisBookBal;
    }

    public void setKismisBookBal(Integer kismisBookBal) {
        this.kismisBookBal = kismisBookBal;
    }

    public Integer getGulamJamPowderGroundBal() {
        return gulamJamPowderGroundBal;
    }

    public void setGulamJamPowderGroundBal(Integer gulamJamPowderGroundBal) {
        this.gulamJamPowderGroundBal = gulamJamPowderGroundBal;
    }

    public Integer getSambarPowderGroundBal() {
        return sambarPowderGroundBal;
    }

    public void setSambarPowderGroundBal(Integer sambarPowderGroundBal) {
        this.sambarPowderGroundBal = sambarPowderGroundBal;
    }

    public Integer getNoodlesBookBal() {
        return noodlesBookBal;
    }

    public void setNoodlesBookBal(Integer noodlesBookBal) {
        this.noodlesBookBal = noodlesBookBal;
    }

    public Integer getGarlicGroundBal() {
        return garlicGroundBal;
    }

    public void setGarlicGroundBal(Integer garlicGroundBal) {
        this.garlicGroundBal = garlicGroundBal;
    }

    public Integer getTillGroundBal() {
        return tillGroundBal;
    }

    public void setTillGroundBal(Integer tillGroundBal) {
        this.tillGroundBal = tillGroundBal;
    }

    public Integer getDaldaBookBal() {
        return daldaBookBal;
    }

    public void setDaldaBookBal(Integer daldaBookBal) {
        this.daldaBookBal = daldaBookBal;
    }

    public Integer getDryCoconutPowderBookBal() {
        return dryCoconutPowderBookBal;
    }

    public void setDryCoconutPowderBookBal(Integer dryCoconutPowderBookBal) {
        this.dryCoconutPowderBookBal = dryCoconutPowderBookBal;
    }

    public Integer getMuramuraluBookBal() {
        return muramuraluBookBal;
    }

    public void setMuramuraluBookBal(Integer muramuraluBookBal) {
        this.muramuraluBookBal = muramuraluBookBal;
    }

    public Integer getCabbageGroundBal() {
        return cabbageGroundBal;
    }

    public void setCabbageGroundBal(Integer cabbageGroundBal) {
        this.cabbageGroundBal = cabbageGroundBal;
    }

    public Integer getDhaniaBookBal() {
        return dhaniaBookBal;
    }

    public void setDhaniaBookBal(Integer dhaniaBookBal) {
        this.dhaniaBookBal = dhaniaBookBal;
    }

    public Integer getDryCoconutBookBal() {
        return dryCoconutBookBal;
    }

    public void setDryCoconutBookBal(Integer dryCoconutBookBal) {
        this.dryCoconutBookBal = dryCoconutBookBal;
    }

    public Integer getSweetPotatoBookBal() {
        return sweetPotatoBookBal;
    }

    public void setSweetPotatoBookBal(Integer sweetPotatoBookBal) {
        this.sweetPotatoBookBal = sweetPotatoBookBal;
    }

    public Integer getWheatRavvaBookBal() {
        return wheatRavvaBookBal;
    }

    public void setWheatRavvaBookBal(Integer wheatRavvaBookBal) {
        this.wheatRavvaBookBal = wheatRavvaBookBal;
    }

    public Integer getMillMeakerGroundBal() {
        return millMeakerGroundBal;
    }

    public void setMillMeakerGroundBal(Integer millMeakerGroundBal) {
        this.millMeakerGroundBal = millMeakerGroundBal;
    }

    public Integer getGasaluGroundBal() {
        return gasaluGroundBal;
    }

    public void setGasaluGroundBal(Integer gasaluGroundBal) {
        this.gasaluGroundBal = gasaluGroundBal;
    }

    public Integer getRagiVermicelliGroundBal() {
        return ragiVermicelliGroundBal;
    }

    public void setRagiVermicelliGroundBal(Integer ragiVermicelliGroundBal) {
        this.ragiVermicelliGroundBal = ragiVermicelliGroundBal;
    }

    public Integer getSplFruitBookBal() {
        return splFruitBookBal;
    }

    public void setSplFruitBookBal(Integer splFruitBookBal) {
        this.splFruitBookBal = splFruitBookBal;
    }

    public Integer getFireWoodBookBal() {
        return fireWoodBookBal;
    }

    public void setFireWoodBookBal(Integer fireWoodBookBal) {
        this.fireWoodBookBal = fireWoodBookBal;
    }

    public Integer getBgAttaGroundBal() {
        return bgAttaGroundBal;
    }

    public void setBgAttaGroundBal(Integer bgAttaGroundBal) {
        this.bgAttaGroundBal = bgAttaGroundBal;
    }

    public Integer getSugarGroundBal() {
        return sugarGroundBal;
    }

    public void setSugarGroundBal(Integer sugarGroundBal) {
        this.sugarGroundBal = sugarGroundBal;
    }

    public Integer getApSnakFoodBookBal() {
        return apSnakFoodBookBal;
    }

    public void setApSnakFoodBookBal(Integer apSnakFoodBookBal) {
        this.apSnakFoodBookBal = apSnakFoodBookBal;
    }

    public Integer getBengalGramRoastGroundBal() {
        return bengalGramRoastGroundBal;
    }

    public void setBengalGramRoastGroundBal(Integer bengalGramRoastGroundBal) {
        this.bengalGramRoastGroundBal = bengalGramRoastGroundBal;
    }

    public Integer getUpmaRavvaBookBal() {
        return upmaRavvaBookBal;
    }

    public void setUpmaRavvaBookBal(Integer upmaRavvaBookBal) {
        this.upmaRavvaBookBal = upmaRavvaBookBal;
    }

    public Integer getGroundNutsBookBal() {
        return groundNutsBookBal;
    }

    public void setGroundNutsBookBal(Integer groundNutsBookBal) {
        this.groundNutsBookBal = groundNutsBookBal;
    }

    public Integer getRedChilliPowderBookBal() {
        return redChilliPowderBookBal;
    }

    public void setRedChilliPowderBookBal(Integer redChilliPowderBookBal) {
        this.redChilliPowderBookBal = redChilliPowderBookBal;
    }

    public Integer getMilkBreadBookBal() {
        return milkBreadBookBal;
    }

    public void setMilkBreadBookBal(Integer milkBreadBookBal) {
        this.milkBreadBookBal = milkBreadBookBal;
    }

    public Integer getCurdGroundBal() {
        return curdGroundBal;
    }

    public void setCurdGroundBal(Integer curdGroundBal) {
        this.curdGroundBal = curdGroundBal;
    }

    public Integer getPopuluBookBal() {
        return populuBookBal;
    }

    public void setPopuluBookBal(Integer populuBookBal) {
        this.populuBookBal = populuBookBal;
    }

    public Integer getEggsGroundBal() {
        return eggsGroundBal;
    }

    public void setEggsGroundBal(Integer eggsGroundBal) {
        this.eggsGroundBal = eggsGroundBal;
    }

    public Integer getMustardBookBal() {
        return mustardBookBal;
    }

    public void setMustardBookBal(Integer mustardBookBal) {
        this.mustardBookBal = mustardBookBal;
    }

    public Integer getCurdBookBal() {
        return curdBookBal;
    }

    public void setCurdBookBal(Integer curdBookBal) {
        this.curdBookBal = curdBookBal;
    }

    public Integer getGingerBookBal() {
        return gingerBookBal;
    }

    public void setGingerBookBal(Integer gingerBookBal) {
        this.gingerBookBal = gingerBookBal;
    }

    public Integer getDalchanaGroundBal() {
        return dalchanaGroundBal;
    }

    public void setDalchanaGroundBal(Integer dalchanaGroundBal) {
        this.dalchanaGroundBal = dalchanaGroundBal;
    }

    public Integer getBakingPowderBookBal() {
        return bakingPowderBookBal;
    }

    public void setBakingPowderBookBal(Integer bakingPowderBookBal) {
        this.bakingPowderBookBal = bakingPowderBookBal;
    }

    public Integer getSambarPowderBookBal() {
        return sambarPowderBookBal;
    }

    public void setSambarPowderBookBal(Integer sambarPowderBookBal) {
        this.sambarPowderBookBal = sambarPowderBookBal;
    }

    public Integer getWheatFlourGroundBal() {
        return wheatFlourGroundBal;
    }

    public void setWheatFlourGroundBal(Integer wheatFlourGroundBal) {
        this.wheatFlourGroundBal = wheatFlourGroundBal;
    }

    public Integer getSurfGroundBal() {
        return surfGroundBal;
    }

    public void setSurfGroundBal(Integer surfGroundBal) {
        this.surfGroundBal = surfGroundBal;
    }

    public Integer getFireWoodGroundBal() {
        return fireWoodGroundBal;
    }

    public void setFireWoodGroundBal(Integer fireWoodGroundBal) {
        this.fireWoodGroundBal = fireWoodGroundBal;
    }

    public Integer getUpmaMixGroundBal() {
        return upmaMixGroundBal;
    }

    public void setUpmaMixGroundBal(Integer upmaMixGroundBal) {
        this.upmaMixGroundBal = upmaMixGroundBal;
    }

    public Integer getDhaniaGroundBal() {
        return dhaniaGroundBal;
    }

    public void setDhaniaGroundBal(Integer dhaniaGroundBal) {
        this.dhaniaGroundBal = dhaniaGroundBal;
    }

    public Integer getMustardGroundBal() {
        return mustardGroundBal;
    }

    public void setMustardGroundBal(Integer mustardGroundBal) {
        this.mustardGroundBal = mustardGroundBal;
    }

    public Integer getCondimentsGroundBal() {
        return condimentsGroundBal;
    }

    public void setCondimentsGroundBal(Integer condimentsGroundBal) {
        this.condimentsGroundBal = condimentsGroundBal;
    }

    public Integer getRajmaRedGroundBal() {
        return rajmaRedGroundBal;
    }

    public void setRajmaRedGroundBal(Integer rajmaRedGroundBal) {
        this.rajmaRedGroundBal = rajmaRedGroundBal;
    }

    public Integer getSweetOilBookBal() {
        return sweetOilBookBal;
    }

    public void setSweetOilBookBal(Integer sweetOilBookBal) {
        this.sweetOilBookBal = sweetOilBookBal;
    }

    public Integer getMuramuraluGroundBal() {
        return muramuraluGroundBal;
    }

    public void setMuramuraluGroundBal(Integer muramuraluGroundBal) {
        this.muramuraluGroundBal = muramuraluGroundBal;
    }

    public Integer getSaltBookBal() {
        return saltBookBal;
    }

    public void setSaltBookBal(Integer saltBookBal) {
        this.saltBookBal = saltBookBal;
    }

    public Integer getBiscuitsBookBal() {
        return biscuitsBookBal;
    }

    public void setBiscuitsBookBal(Integer biscuitsBookBal) {
        this.biscuitsBookBal = biscuitsBookBal;
    }

    public Integer getPutnaluGroundBal() {
        return putnaluGroundBal;
    }

    public void setPutnaluGroundBal(Integer putnaluGroundBal) {
        this.putnaluGroundBal = putnaluGroundBal;
    }

    public Integer getBatanaBookBal() {
        return batanaBookBal;
    }

    public void setBatanaBookBal(Integer batanaBookBal) {
        this.batanaBookBal = batanaBookBal;
    }

    public Integer getPeasBookBal() {
        return peasBookBal;
    }

    public void setPeasBookBal(Integer peasBookBal) {
        this.peasBookBal = peasBookBal;
    }

    public Integer getDhanniyaPowderBookBal() {
        return dhanniyaPowderBookBal;
    }

    public void setDhanniyaPowderBookBal(Integer dhanniyaPowderBookBal) {
        this.dhanniyaPowderBookBal = dhanniyaPowderBookBal;
    }

    public Double getRedChilliesGroundBal() {
        return redChilliesGroundBal;
    }

    public void setRedChilliesGroundBal(Double redChilliesGroundBal) {
        this.redChilliesGroundBal = redChilliesGroundBal;
    }

    public Integer getRaiBookBal() {
        return raiBookBal;
    }

    public void setRaiBookBal(Integer raiBookBal) {
        this.raiBookBal = raiBookBal;
    }

    public Integer getTransportBookBal() {
        return transportBookBal;
    }

    public void setTransportBookBal(Integer transportBookBal) {
        this.transportBookBal = transportBookBal;
    }

    public Integer getVegetablesBookBal() {
        return vegetablesBookBal;
    }

    public void setVegetablesBookBal(Integer vegetablesBookBal) {
        this.vegetablesBookBal = vegetablesBookBal;
    }

    public Integer getZoharVermisellyGroundBal() {
        return zoharVermisellyGroundBal;
    }

    public void setZoharVermisellyGroundBal(Integer zoharVermisellyGroundBal) {
        this.zoharVermisellyGroundBal = zoharVermisellyGroundBal;
    }

    public Integer getLemonBookBal() {
        return lemonBookBal;
    }

    public void setLemonBookBal(Integer lemonBookBal) {
        this.lemonBookBal = lemonBookBal;
    }

    public Integer getLavangaBookBal() {
        return lavangaBookBal;
    }

    public void setLavangaBookBal(Integer lavangaBookBal) {
        this.lavangaBookBal = lavangaBookBal;
    }

    public Integer getBlackgramDalGroundBal() {
        return blackgramDalGroundBal;
    }

    public void setBlackgramDalGroundBal(Integer blackgramDalGroundBal) {
        this.blackgramDalGroundBal = blackgramDalGroundBal;
    }

    public Integer getEggsBookBal() {
        return eggsBookBal;
    }

    public void setEggsBookBal(Integer eggsBookBal) {
        this.eggsBookBal = eggsBookBal;
    }

    public Integer getBgAttaBookBal() {
        return bgAttaBookBal;
    }

    public void setBgAttaBookBal(Integer bgAttaBookBal) {
        this.bgAttaBookBal = bgAttaBookBal;
    }

    public Integer getChanadalBookBal() {
        return chanadalBookBal;
    }

    public void setChanadalBookBal(Integer chanadalBookBal) {
        this.chanadalBookBal = chanadalBookBal;
    }

    public Integer getUpmaRavvaGroundBal() {
        return upmaRavvaGroundBal;
    }

    public void setUpmaRavvaGroundBal(Integer upmaRavvaGroundBal) {
        this.upmaRavvaGroundBal = upmaRavvaGroundBal;
    }

    public Integer getMuttonGroundBal() {
        return muttonGroundBal;
    }

    public void setMuttonGroundBal(Integer muttonGroundBal) {
        this.muttonGroundBal = muttonGroundBal;
    }

    public Integer getKhajuBookBal() {
        return khajuBookBal;
    }

    public void setKhajuBookBal(Integer khajuBookBal) {
        this.khajuBookBal = khajuBookBal;
    }

    public Integer getMilkBookBal() {
        return milkBookBal;
    }

    public void setMilkBookBal(Integer milkBookBal) {
        this.milkBookBal = milkBookBal;
    }

    public Integer getPesarluBookBal() {
        return pesarluBookBal;
    }

    public void setPesarluBookBal(Integer pesarluBookBal) {
        this.pesarluBookBal = pesarluBookBal;
    }

    public Integer getOnionsBookBal() {
        return onionsBookBal;
    }

    public void setOnionsBookBal(Integer onionsBookBal) {
        this.onionsBookBal = onionsBookBal;
    }

    public Integer getPepperGroundBal() {
        return pepperGroundBal;
    }

    public void setPepperGroundBal(Integer pepperGroundBal) {
        this.pepperGroundBal = pepperGroundBal;
    }

    public Integer getZoharCookieesBookBal() {
        return zoharCookieesBookBal;
    }

    public void setZoharCookieesBookBal(Integer zoharCookieesBookBal) {
        this.zoharCookieesBookBal = zoharCookieesBookBal;
    }

    public Integer getGroundnutPalliPattiGroundBal() {
        return groundnutPalliPattiGroundBal;
    }

    public void setGroundnutPalliPattiGroundBal(Integer groundnutPalliPattiGroundBal) {
        this.groundnutPalliPattiGroundBal = groundnutPalliPattiGroundBal;
    }

    public Integer getBazraCookieesGroundBal() {
        return bazraCookieesGroundBal;
    }

    public void setBazraCookieesGroundBal(Integer bazraCookieesGroundBal) {
        this.bazraCookieesGroundBal = bazraCookieesGroundBal;
    }

    public Integer getRaiGroundBal() {
        return raiGroundBal;
    }

    public void setRaiGroundBal(Integer raiGroundBal) {
        this.raiGroundBal = raiGroundBal;
    }

    public Integer getYellowGramDalBookBal() {
        return yellowGramDalBookBal;
    }

    public void setYellowGramDalBookBal(Integer yellowGramDalBookBal) {
        this.yellowGramDalBookBal = yellowGramDalBookBal;
    }

    public Integer getRaithaBookBal() {
        return raithaBookBal;
    }

    public void setRaithaBookBal(Integer raithaBookBal) {
        this.raithaBookBal = raithaBookBal;
    }

    public Integer getBoiledPearsWithOnionsGroundBal() {
        return boiledPearsWithOnionsGroundBal;
    }

    public void setBoiledPearsWithOnionsGroundBal(Integer boiledPearsWithOnionsGroundBal) {
        this.boiledPearsWithOnionsGroundBal = boiledPearsWithOnionsGroundBal;
    }

    public Integer getAllBookBal() {
        return allBookBal;
    }

    public void setAllBookBal(Integer allBookBal) {
        this.allBookBal = allBookBal;
    }

    public Integer getBengalGramRoastBookBal() {
        return bengalGramRoastBookBal;
    }

    public void setBengalGramRoastBookBal(Integer bengalGramRoastBookBal) {
        this.bengalGramRoastBookBal = bengalGramRoastBookBal;
    }

    public Integer getBeensGroundBal() {
        return beensGroundBal;
    }

    public void setBeensGroundBal(Integer beensGroundBal) {
        this.beensGroundBal = beensGroundBal;
    }

    public Integer getJowarGroundBal() {
        return jowarGroundBal;
    }

    public void setJowarGroundBal(Integer jowarGroundBal) {
        this.jowarGroundBal = jowarGroundBal;
    }

    public Integer getOnionsGroundBal() {
        return onionsGroundBal;
    }

    public void setOnionsGroundBal(Integer onionsGroundBal) {
        this.onionsGroundBal = onionsGroundBal;
    }

    public Integer getIdlyRavaBookBal() {
        return idlyRavaBookBal;
    }

    public void setIdlyRavaBookBal(Integer idlyRavaBookBal) {
        this.idlyRavaBookBal = idlyRavaBookBal;
    }

    public Integer getMilkBreadGroundBal() {
        return milkBreadGroundBal;
    }

    public void setMilkBreadGroundBal(Integer milkBreadGroundBal) {
        this.milkBreadGroundBal = milkBreadGroundBal;
    }

    public Integer getGarlicJingerPasteGroundBal() {
        return garlicJingerPasteGroundBal;
    }

    public void setGarlicJingerPasteGroundBal(Integer garlicJingerPasteGroundBal) {
        this.garlicJingerPasteGroundBal = garlicJingerPasteGroundBal;
    }

    public Integer getBazraCookieesBookBal() {
        return bazraCookieesBookBal;
    }

    public void setBazraCookieesBookBal(Integer bazraCookieesBookBal) {
        this.bazraCookieesBookBal = bazraCookieesBookBal;
    }

    public Integer getBansiRavvaGroundBal() {
        return bansiRavvaGroundBal;
    }

    public void setBansiRavvaGroundBal(Integer bansiRavvaGroundBal) {
        this.bansiRavvaGroundBal = bansiRavvaGroundBal;
    }

    public Integer getDooduAtukuluGroundBal() {
        return dooduAtukuluGroundBal;
    }

    public void setDooduAtukuluGroundBal(Integer dooduAtukuluGroundBal) {
        this.dooduAtukuluGroundBal = dooduAtukuluGroundBal;
    }

    public Integer getRackSaltBookBal() {
        return rackSaltBookBal;
    }

    public void setRackSaltBookBal(Integer rackSaltBookBal) {
        this.rackSaltBookBal = rackSaltBookBal;
    }

    public Integer getBakingPowderGroundBal() {
        return bakingPowderGroundBal;
    }

    public void setBakingPowderGroundBal(Integer bakingPowderGroundBal) {
        this.bakingPowderGroundBal = bakingPowderGroundBal;
    }

    public Integer getRedChilliPowderGroundBal() {
        return redChilliPowderGroundBal;
    }

    public void setRedChilliPowderGroundBal(Integer redChilliPowderGroundBal) {
        this.redChilliPowderGroundBal = redChilliPowderGroundBal;
    }

    public Integer getSweetPorridgeGroundBal() {
        return sweetPorridgeGroundBal;
    }

    public void setSweetPorridgeGroundBal(Integer sweetPorridgeGroundBal) {
        this.sweetPorridgeGroundBal = sweetPorridgeGroundBal;
    }

    public Integer getRagimaltGroundBal() {
        return ragimaltGroundBal;
    }

    public void setRagimaltGroundBal(Integer ragimaltGroundBal) {
        this.ragimaltGroundBal = ragimaltGroundBal;
    }

    public Integer getPulihoraPowderGroundBal() {
        return pulihoraPowderGroundBal;
    }

    public void setPulihoraPowderGroundBal(Integer pulihoraPowderGroundBal) {
        this.pulihoraPowderGroundBal = pulihoraPowderGroundBal;
    }

    public Integer getBiscuitsGroundBal() {
        return biscuitsGroundBal;
    }

    public void setBiscuitsGroundBal(Integer biscuitsGroundBal) {
        this.biscuitsGroundBal = biscuitsGroundBal;
    }

    public Integer getFishBookBal() {
        return fishBookBal;
    }

    public void setFishBookBal(Integer fishBookBal) {
        this.fishBookBal = fishBookBal;
    }

    public Double getTurmericPowderBookBal() {
        return turmericPowderBookBal;
    }

    public void setTurmericPowderBookBal(Double turmericPowderBookBal) {
        this.turmericPowderBookBal = turmericPowderBookBal;
    }

    public Integer getBlackgramDalBookBal() {
        return blackgramDalBookBal;
    }

    public void setBlackgramDalBookBal(Integer blackgramDalBookBal) {
        this.blackgramDalBookBal = blackgramDalBookBal;
    }

    public Integer getKhajuGroundBal() {
        return khajuGroundBal;
    }

    public void setKhajuGroundBal(Integer khajuGroundBal) {
        this.khajuGroundBal = khajuGroundBal;
    }

    public Integer getMilkPowderBookBal() {
        return milkPowderBookBal;
    }

    public void setMilkPowderBookBal(Integer milkPowderBookBal) {
        this.milkPowderBookBal = milkPowderBookBal;
    }

    public Integer getLavangaGroundBal() {
        return lavangaGroundBal;
    }

    public void setLavangaGroundBal(Integer lavangaGroundBal) {
        this.lavangaGroundBal = lavangaGroundBal;
    }

    public Integer getAtukuluBookBal() {
        return atukuluBookBal;
    }

    public void setAtukuluBookBal(Integer atukuluBookBal) {
        this.atukuluBookBal = atukuluBookBal;
    }

    public Integer getChickenGroundBal() {
        return chickenGroundBal;
    }

    public void setChickenGroundBal(Integer chickenGroundBal) {
        this.chickenGroundBal = chickenGroundBal;
    }

    public Integer getLemonGroundBal() {
        return lemonGroundBal;
    }

    public void setLemonGroundBal(Integer lemonGroundBal) {
        this.lemonGroundBal = lemonGroundBal;
    }

    public Integer getSweetPotatoGroundBal() {
        return sweetPotatoGroundBal;
    }

    public void setSweetPotatoGroundBal(Integer sweetPotatoGroundBal) {
        this.sweetPotatoGroundBal = sweetPotatoGroundBal;
    }

    public Integer getDryCoconutGroundBal() {
        return dryCoconutGroundBal;
    }

    public void setDryCoconutGroundBal(Integer dryCoconutGroundBal) {
        this.dryCoconutGroundBal = dryCoconutGroundBal;
    }

    public Integer getGarlicJingerPasteBookBal() {
        return garlicJingerPasteBookBal;
    }

    public void setGarlicJingerPasteBookBal(Integer garlicJingerPasteBookBal) {
        this.garlicJingerPasteBookBal = garlicJingerPasteBookBal;
    }

    public Integer getSagguRiceBookBal() {
        return sagguRiceBookBal;
    }

    public void setSagguRiceBookBal(Integer sagguRiceBookBal) {
        this.sagguRiceBookBal = sagguRiceBookBal;
    }

    public Integer getAtukuluGroundBal() {
        return atukuluGroundBal;
    }

    public void setAtukuluGroundBal(Integer atukuluGroundBal) {
        this.atukuluGroundBal = atukuluGroundBal;
    }

    public Integer getMilkPowderGroundBal() {
        return milkPowderGroundBal;
    }

    public void setMilkPowderGroundBal(Integer milkPowderGroundBal) {
        this.milkPowderGroundBal = milkPowderGroundBal;
    }

    public Integer getPopuluGroundBal() {
        return populuGroundBal;
    }

    public void setPopuluGroundBal(Integer populuGroundBal) {
        this.populuGroundBal = populuGroundBal;
    }

    public Integer getPickleGroundBal() {
        return pickleGroundBal;
    }

    public void setPickleGroundBal(Integer pickleGroundBal) {
        this.pickleGroundBal = pickleGroundBal;
    }

    public Integer getUdathDallGroundBal() {
        return udathDallGroundBal;
    }

    public void setUdathDallGroundBal(Integer udathDallGroundBal) {
        this.udathDallGroundBal = udathDallGroundBal;
    }

    public Integer getMoongDalGroundBal() {
        return moongDalGroundBal;
    }

    public void setMoongDalGroundBal(Integer moongDalGroundBal) {
        this.moongDalGroundBal = moongDalGroundBal;
    }

    public Integer getLemonSaltGroundBal() {
        return lemonSaltGroundBal;
    }

    public void setLemonSaltGroundBal(Integer lemonSaltGroundBal) {
        this.lemonSaltGroundBal = lemonSaltGroundBal;
    }

    public Integer getBombaiRavaBookBal() {
        return bombaiRavaBookBal;
    }

    public void setBombaiRavaBookBal(Integer bombaiRavaBookBal) {
        this.bombaiRavaBookBal = bombaiRavaBookBal;
    }

    public Integer getButterGroundBal() {
        return butterGroundBal;
    }

    public void setButterGroundBal(Integer butterGroundBal) {
        this.butterGroundBal = butterGroundBal;
    }

    public Integer getTransportGroundBal() {
        return transportGroundBal;
    }

    public void setTransportGroundBal(Integer transportGroundBal) {
        this.transportGroundBal = transportGroundBal;
    }

    public Integer getCusterPowederBookBal() {
        return custerPowederBookBal;
    }

    public void setCusterPowederBookBal(Integer custerPowederBookBal) {
        this.custerPowederBookBal = custerPowederBookBal;
    }

    public Integer getBoostBookBal() {
        return boostBookBal;
    }

    public void setBoostBookBal(Integer boostBookBal) {
        this.boostBookBal = boostBookBal;
    }

    public Integer getSweetPorridgeBookBal() {
        return sweetPorridgeBookBal;
    }

    public void setSweetPorridgeBookBal(Integer sweetPorridgeBookBal) {
        this.sweetPorridgeBookBal = sweetPorridgeBookBal;
    }

    public Integer getPulkaBookBal() {
        return pulkaBookBal;
    }

    public void setPulkaBookBal(Integer pulkaBookBal) {
        this.pulkaBookBal = pulkaBookBal;
    }

    public Integer getJaggeryGroundBal() {
        return jaggeryGroundBal;
    }

    public void setJaggeryGroundBal(Integer jaggeryGroundBal) {
        this.jaggeryGroundBal = jaggeryGroundBal;
    }

    public Integer getBengalgramFlourBookBal() {
        return bengalgramFlourBookBal;
    }

    public void setBengalgramFlourBookBal(Integer bengalgramFlourBookBal) {
        this.bengalgramFlourBookBal = bengalgramFlourBookBal;
    }

    public Integer getBengalGramDalBookBal() {
        return bengalGramDalBookBal;
    }

    public void setBengalGramDalBookBal(Integer bengalGramDalBookBal) {
        this.bengalGramDalBookBal = bengalGramDalBookBal;
    }

    public Integer getSoanPapidiGroundBal() {
        return soanPapidiGroundBal;
    }

    public void setSoanPapidiGroundBal(Integer soanPapidiGroundBal) {
        this.soanPapidiGroundBal = soanPapidiGroundBal;
    }

    public Integer getKichidiMixGroundBal() {
        return kichidiMixGroundBal;
    }

    public void setKichidiMixGroundBal(Integer kichidiMixGroundBal) {
        this.kichidiMixGroundBal = kichidiMixGroundBal;
    }

    public Integer getIlachiGroundBal() {
        return ilachiGroundBal;
    }

    public void setIlachiGroundBal(Integer ilachiGroundBal) {
        this.ilachiGroundBal = ilachiGroundBal;
    }

    public Integer getMasalaBookBal() {
        return masalaBookBal;
    }

    public void setMasalaBookBal(Integer masalaBookBal) {
        this.masalaBookBal = masalaBookBal;
    }

    public Integer getDhanniyaPowderGroundBal() {
        return dhanniyaPowderGroundBal;
    }

    public void setDhanniyaPowderGroundBal(Integer dhanniyaPowderGroundBal) {
        this.dhanniyaPowderGroundBal = dhanniyaPowderGroundBal;
    }

    public Integer getMilkGroundBal() {
        return milkGroundBal;
    }

    public void setMilkGroundBal(Integer milkGroundBal) {
        this.milkGroundBal = milkGroundBal;
    }

    public Integer getZoharCookieesGroundBal() {
        return zoharCookieesGroundBal;
    }

    public void setZoharCookieesGroundBal(Integer zoharCookieesGroundBal) {
        this.zoharCookieesGroundBal = zoharCookieesGroundBal;
    }

    public Integer getSajeeraGroundBal() {
        return sajeeraGroundBal;
    }

    public void setSajeeraGroundBal(Integer sajeeraGroundBal) {
        this.sajeeraGroundBal = sajeeraGroundBal;
    }

    public Integer getButterBookBal() {
        return butterBookBal;
    }

    public void setButterBookBal(Integer butterBookBal) {
        this.butterBookBal = butterBookBal;
    }

    public Integer getHandwashLiquidBookBal() {
        return handwashLiquidBookBal;
    }

    public void setHandwashLiquidBookBal(Integer handwashLiquidBookBal) {
        this.handwashLiquidBookBal = handwashLiquidBookBal;
    }

    public Integer getBeensBookBal() {
        return beensBookBal;
    }

    public void setBeensBookBal(Integer beensBookBal) {
        this.beensBookBal = beensBookBal;
    }

    public Integer getJowarBookBal() {
        return jowarBookBal;
    }

    public void setJowarBookBal(Integer jowarBookBal) {
        this.jowarBookBal = jowarBookBal;
    }

    public Integer getPickleBookBal() {
        return pickleBookBal;
    }

    public void setPickleBookBal(Integer pickleBookBal) {
        this.pickleBookBal = pickleBookBal;
    }

    public Integer getGasGroundBal() {
        return gasGroundBal;
    }

    public void setGasGroundBal(Integer gasGroundBal) {
        this.gasGroundBal = gasGroundBal;
    }

    public Integer getRagimaltBookBal() {
        return ragimaltBookBal;
    }

    public void setRagimaltBookBal(Integer ragimaltBookBal) {
        this.ragimaltBookBal = ragimaltBookBal;
    }

    public Integer getMenthuluBookBal() {
        return menthuluBookBal;
    }

    public void setMenthuluBookBal(Integer menthuluBookBal) {
        this.menthuluBookBal = menthuluBookBal;
    }

    public Integer getRedGramDallGroundBal() {
        return redGramDallGroundBal;
    }

    public void setRedGramDallGroundBal(Integer redGramDallGroundBal) {
        this.redGramDallGroundBal = redGramDallGroundBal;
    }

    public Integer getBatanaGroundBal() {
        return batanaGroundBal;
    }

    public void setBatanaGroundBal(Integer batanaGroundBal) {
        this.batanaGroundBal = batanaGroundBal;
    }

    public Integer getCusterPowederGroundBal() {
        return custerPowederGroundBal;
    }

    public void setCusterPowederGroundBal(Integer custerPowederGroundBal) {
        this.custerPowederGroundBal = custerPowederGroundBal;
    }

    public Integer getBengalGramDalGroundBal() {
        return bengalGramDalGroundBal;
    }

    public void setBengalGramDalGroundBal(Integer bengalGramDalGroundBal) {
        this.bengalGramDalGroundBal = bengalGramDalGroundBal;
    }

    public Integer getVermicelliBookBal() {
        return vermicelliBookBal;
    }

    public void setVermicelliBookBal(Integer vermicelliBookBal) {
        this.vermicelliBookBal = vermicelliBookBal;
    }

    public Integer getBajraGroundBal() {
        return bajraGroundBal;
    }

    public void setBajraGroundBal(Integer bajraGroundBal) {
        this.bajraGroundBal = bajraGroundBal;
    }

    public Integer getRagiMixerGroundBal() {
        return ragiMixerGroundBal;
    }

    public void setRagiMixerGroundBal(Integer ragiMixerGroundBal) {
        this.ragiMixerGroundBal = ragiMixerGroundBal;
    }

    public Integer getTamarindGroundBal() {
        return tamarindGroundBal;
    }

    public void setTamarindGroundBal(Integer tamarindGroundBal) {
        this.tamarindGroundBal = tamarindGroundBal;
    }

    public Integer getBobbarluGroundBal() {
        return bobbarluGroundBal;
    }

    public void setBobbarluGroundBal(Integer bobbarluGroundBal) {
        this.bobbarluGroundBal = bobbarluGroundBal;
    }

    public Integer getPeasGroundBal() {
        return peasGroundBal;
    }

    public void setPeasGroundBal(Integer peasGroundBal) {
        this.peasGroundBal = peasGroundBal;
    }

    public Integer getApSnakFoodGroundBal() {
        return apSnakFoodGroundBal;
    }

    public void setApSnakFoodGroundBal(Integer apSnakFoodGroundBal) {
        this.apSnakFoodGroundBal = apSnakFoodGroundBal;
    }

    public Integer getGulamJamPowderBookBal() {
        return gulamJamPowderBookBal;
    }

    public void setGulamJamPowderBookBal(Integer gulamJamPowderBookBal) {
        this.gulamJamPowderBookBal = gulamJamPowderBookBal;
    }

    public Integer getVegetablesGroundBal() {
        return vegetablesGroundBal;
    }

    public void setVegetablesGroundBal(Integer vegetablesGroundBal) {
        this.vegetablesGroundBal = vegetablesGroundBal;
    }

    public Integer getTillBookBal() {
        return tillBookBal;
    }

    public void setTillBookBal(Integer tillBookBal) {
        this.tillBookBal = tillBookBal;
    }

    public Integer getSweetOilGroundBal() {
        return sweetOilGroundBal;
    }

    public void setSweetOilGroundBal(Integer sweetOilGroundBal) {
        this.sweetOilGroundBal = sweetOilGroundBal;
    }

    public Integer getTeaPowderBookBal() {
        return teaPowderBookBal;
    }

    public void setTeaPowderBookBal(Integer teaPowderBookBal) {
        this.teaPowderBookBal = teaPowderBookBal;
    }

    public Integer getBombaiRavaGroundBal() {
        return bombaiRavaGroundBal;
    }

    public void setBombaiRavaGroundBal(Integer bombaiRavaGroundBal) {
        this.bombaiRavaGroundBal = bombaiRavaGroundBal;
    }

    public Integer getZeeraBookBal() {
        return zeeraBookBal;
    }

    public void setZeeraBookBal(Integer zeeraBookBal) {
        this.zeeraBookBal = zeeraBookBal;
    }

    public Integer getDalchanaBookBal() {
        return dalchanaBookBal;
    }

    public void setDalchanaBookBal(Integer dalchanaBookBal) {
        this.dalchanaBookBal = dalchanaBookBal;
    }

    public Integer getSanagaluGroundBal() {
        return sanagaluGroundBal;
    }

    public void setSanagaluGroundBal(Integer sanagaluGroundBal) {
        this.sanagaluGroundBal = sanagaluGroundBal;
    }

    public Integer getNoodlesGroundBal() {
        return noodlesGroundBal;
    }

    public void setNoodlesGroundBal(Integer noodlesGroundBal) {
        this.noodlesGroundBal = noodlesGroundBal;
    }

    public Integer getPutnaluBookBal() {
        return putnaluBookBal;
    }

    public void setPutnaluBookBal(Integer putnaluBookBal) {
        this.putnaluBookBal = putnaluBookBal;
    }

    public Integer getAlasanduluGroundBal() {
        return alasanduluGroundBal;
    }

    public void setAlasanduluGroundBal(Integer alasanduluGroundBal) {
        this.alasanduluGroundBal = alasanduluGroundBal;
    }

    public Integer getBansiRavvaBookBal() {
        return bansiRavvaBookBal;
    }

    public void setBansiRavvaBookBal(Integer bansiRavvaBookBal) {
        this.bansiRavvaBookBal = bansiRavvaBookBal;
    }

    public Integer getCondimentsBookBal() {
        return condimentsBookBal;
    }

    public void setCondimentsBookBal(Integer condimentsBookBal) {
        this.condimentsBookBal = condimentsBookBal;
    }

    public Integer getBobbarluBookBal() {
        return bobbarluBookBal;
    }

    public void setBobbarluBookBal(Integer bobbarluBookBal) {
        this.bobbarluBookBal = bobbarluBookBal;
    }

    public Integer getGasaluBookBal() {
        return gasaluBookBal;
    }

    public void setGasaluBookBal(Integer gasaluBookBal) {
        this.gasaluBookBal = gasaluBookBal;
    }

    public Integer getPulkaGroundBal() {
        return pulkaGroundBal;
    }

    public void setPulkaGroundBal(Integer pulkaGroundBal) {
        this.pulkaGroundBal = pulkaGroundBal;
    }

    public Integer getIdlyMixWeeklyTwiceGroundBal() {
        return idlyMixWeeklyTwiceGroundBal;
    }

    public void setIdlyMixWeeklyTwiceGroundBal(Integer idlyMixWeeklyTwiceGroundBal) {
        this.idlyMixWeeklyTwiceGroundBal = idlyMixWeeklyTwiceGroundBal;
    }

    public Integer getSanagaluBookBal() {
        return sanagaluBookBal;
    }

    public void setSanagaluBookBal(Integer sanagaluBookBal) {
        this.sanagaluBookBal = sanagaluBookBal;
    }

    public Integer getMuttonBookBal() {
        return muttonBookBal;
    }

    public void setMuttonBookBal(Integer muttonBookBal) {
        this.muttonBookBal = muttonBookBal;
    }

    public Integer getRasampowderGroundBal() {
        return rasampowderGroundBal;
    }

    public void setRasampowderGroundBal(Integer rasampowderGroundBal) {
        this.rasampowderGroundBal = rasampowderGroundBal;
    }

    public Integer getGingerGroundBal() {
        return gingerGroundBal;
    }

    public void setGingerGroundBal(Integer gingerGroundBal) {
        this.gingerGroundBal = gingerGroundBal;
    }

    public Integer getGroundnutPalliPattiBookBal() {
        return groundnutPalliPattiBookBal;
    }

    public void setGroundnutPalliPattiBookBal(Integer groundnutPalliPattiBookBal) {
        this.groundnutPalliPattiBookBal = groundnutPalliPattiBookBal;
    }

    public Integer getBagaraLeafGroundBal() {
        return bagaraLeafGroundBal;
    }

    public void setBagaraLeafGroundBal(Integer bagaraLeafGroundBal) {
        this.bagaraLeafGroundBal = bagaraLeafGroundBal;
    }

    public Integer getGheeGroundBal() {
        return gheeGroundBal;
    }

    public void setGheeGroundBal(Integer gheeGroundBal) {
        this.gheeGroundBal = gheeGroundBal;
    }

    public Integer getPepperBookBal() {
        return pepperBookBal;
    }

    public void setPepperBookBal(Integer pepperBookBal) {
        this.pepperBookBal = pepperBookBal;
    }

    public Integer getSweetBookBal() {
        return sweetBookBal;
    }

    public void setSweetBookBal(Integer sweetBookBal) {
        this.sweetBookBal = sweetBookBal;
    }

    public Integer getPOilBookBal() {
        return pOilBookBal;
    }

    public void setPOilBookBal(Integer pOilBookBal) {
        this.pOilBookBal = pOilBookBal;
    }

    public Integer getCorrianderBookBal() {
        return corrianderBookBal;
    }

    public void setCorrianderBookBal(Integer corrianderBookBal) {
        this.corrianderBookBal = corrianderBookBal;
    }

    public Integer getSaltGroundBal() {
        return saltGroundBal;
    }

    public void setSaltGroundBal(Integer saltGroundBal) {
        this.saltGroundBal = saltGroundBal;
    }

    public Integer getGreenPeasBookBal() {
        return greenPeasBookBal;
    }

    public void setGreenPeasBookBal(Integer greenPeasBookBal) {
        this.greenPeasBookBal = greenPeasBookBal;
    }

    public Integer getLadysFingerBookBal() {
        return ladysFingerBookBal;
    }

    public void setLadysFingerBookBal(Integer ladysFingerBookBal) {
        this.ladysFingerBookBal = ladysFingerBookBal;
    }

    public Integer getPesarluGroundBal() {
        return pesarluGroundBal;
    }

    public void setPesarluGroundBal(Integer pesarluGroundBal) {
        this.pesarluGroundBal = pesarluGroundBal;
    }

    public Integer getRackSaltGroundBal() {
        return rackSaltGroundBal;
    }

    public void setRackSaltGroundBal(Integer rackSaltGroundBal) {
        this.rackSaltGroundBal = rackSaltGroundBal;
    }

    public Integer getKichidiMixBookBal() {
        return kichidiMixBookBal;
    }

    public void setKichidiMixBookBal(Integer kichidiMixBookBal) {
        this.kichidiMixBookBal = kichidiMixBookBal;
    }

    public Integer getBananaBookBal() {
        return bananaBookBal;
    }

    public void setBananaBookBal(Integer bananaBookBal) {
        this.bananaBookBal = bananaBookBal;
    }

    public Integer getMasalaGroundBal() {
        return masalaGroundBal;
    }

    public void setMasalaGroundBal(Integer masalaGroundBal) {
        this.masalaGroundBal = masalaGroundBal;
    }

    public Integer getPapadGroundBal() {
        return papadGroundBal;
    }

    public void setPapadGroundBal(Integer papadGroundBal) {
        this.papadGroundBal = papadGroundBal;
    }

    public Integer getMearyGoldMilletGroundBal() {
        return mearyGoldMilletGroundBal;
    }

    public void setMearyGoldMilletGroundBal(Integer mearyGoldMilletGroundBal) {
        this.mearyGoldMilletGroundBal = mearyGoldMilletGroundBal;
    }

    public Integer getUdathDallBookBal() {
        return udathDallBookBal;
    }

    public void setUdathDallBookBal(Integer udathDallBookBal) {
        this.udathDallBookBal = udathDallBookBal;
    }

    public Integer getWheatBookBal() {
        return wheatBookBal;
    }

    public void setWheatBookBal(Integer wheatBookBal) {
        this.wheatBookBal = wheatBookBal;
    }

    public Integer getDaldaGroundBal() {
        return daldaGroundBal;
    }

    public void setDaldaGroundBal(Integer daldaGroundBal) {
        this.daldaGroundBal = daldaGroundBal;
    }

    public Integer getTamarindBookBal() {
        return tamarindBookBal;
    }

    public void setTamarindBookBal(Integer tamarindBookBal) {
        this.tamarindBookBal = tamarindBookBal;
    }

    public Integer getSagguRiceGroundBal() {
        return sagguRiceGroundBal;
    }

    public void setSagguRiceGroundBal(Integer sagguRiceGroundBal) {
        this.sagguRiceGroundBal = sagguRiceGroundBal;
    }

    public Integer getRagiMixerBookBal() {
        return ragiMixerBookBal;
    }

    public void setRagiMixerBookBal(Integer ragiMixerBookBal) {
        this.ragiMixerBookBal = ragiMixerBookBal;
    }

    public Integer getBoiledPearsWithOnionsBookBal() {
        return boiledPearsWithOnionsBookBal;
    }

    public void setBoiledPearsWithOnionsBookBal(Integer boiledPearsWithOnionsBookBal) {
        this.boiledPearsWithOnionsBookBal = boiledPearsWithOnionsBookBal;
    }

    public Integer getKismisGroundBal() {
        return kismisGroundBal;
    }

    public void setKismisGroundBal(Integer kismisGroundBal) {
        this.kismisGroundBal = kismisGroundBal;
    }

    public Integer getZoharVermisellyBookBal() {
        return zoharVermisellyBookBal;
    }

    public void setZoharVermisellyBookBal(Integer zoharVermisellyBookBal) {
        this.zoharVermisellyBookBal = zoharVermisellyBookBal;
    }

    public Integer getBreadBookBal() {
        return breadBookBal;
    }

    public void setBreadBookBal(Integer breadBookBal) {
        this.breadBookBal = breadBookBal;
    }

    public Integer getSoanPapidiBookBal() {
        return soanPapidiBookBal;
    }

    public void setSoanPapidiBookBal(Integer soanPapidiBookBal) {
        this.soanPapidiBookBal = soanPapidiBookBal;
    }

    public Integer getGroundNutsGroundBal() {
        return groundNutsGroundBal;
    }

    public void setGroundNutsGroundBal(Integer groundNutsGroundBal) {
        this.groundNutsGroundBal = groundNutsGroundBal;
    }

    public Integer getYellowGramDalGroundBal() {
        return yellowGramDalGroundBal;
    }

    public void setYellowGramDalGroundBal(Integer yellowGramDalGroundBal) {
        this.yellowGramDalGroundBal = yellowGramDalGroundBal;
    }

    public Integer getLadysFingerGroundBal() {
        return ladysFingerGroundBal;
    }

    public void setLadysFingerGroundBal(Integer ladysFingerGroundBal) {
        this.ladysFingerGroundBal = ladysFingerGroundBal;
    }

    public Integer getGGDalBookBal() {
        return gGDalBookBal;
    }

    public void setGGDalBookBal(Integer gGDalBookBal) {
        this.gGDalBookBal = gGDalBookBal;
    }

    public Integer getCabbageBookBal() {
        return cabbageBookBal;
    }

    public void setCabbageBookBal(Integer cabbageBookBal) {
        this.cabbageBookBal = cabbageBookBal;
    }

    public Integer getMenthuluGroundBal() {
        return menthuluGroundBal;
    }

    public void setMenthuluGroundBal(Integer menthuluGroundBal) {
        this.menthuluGroundBal = menthuluGroundBal;
    }

    public Double getTurmericPowderGroundBal() {
        return turmericPowderGroundBal;
    }

    public void setTurmericPowderGroundBal(Double turmericPowderGroundBal) {
        this.turmericPowderGroundBal = turmericPowderGroundBal;
    }

    public Integer getLemonSaltBookBal() {
        return lemonSaltBookBal;
    }

    public void setLemonSaltBookBal(Integer lemonSaltBookBal) {
        this.lemonSaltBookBal = lemonSaltBookBal;
    }

    public Integer getRaithaGroundBal() {
        return raithaGroundBal;
    }

    public void setRaithaGroundBal(Integer raithaGroundBal) {
        this.raithaGroundBal = raithaGroundBal;
    }

    public Integer getWheatRavvaGroundBal() {
        return wheatRavvaGroundBal;
    }

    public void setWheatRavvaGroundBal(Integer wheatRavvaGroundBal) {
        this.wheatRavvaGroundBal = wheatRavvaGroundBal;
    }

    public Integer getDooduAtukuluBookBal() {
        return dooduAtukuluBookBal;
    }

    public void setDooduAtukuluBookBal(Integer dooduAtukuluBookBal) {
        this.dooduAtukuluBookBal = dooduAtukuluBookBal;
    }

    public Integer getBaadamBookBal() {
        return baadamBookBal;
    }

    public void setBaadamBookBal(Integer baadamBookBal) {
        this.baadamBookBal = baadamBookBal;
    }

    public Integer getWheatFlourBookBal() {
        return wheatFlourBookBal;
    }

    public void setWheatFlourBookBal(Integer wheatFlourBookBal) {
        this.wheatFlourBookBal = wheatFlourBookBal;
    }

    public Integer getSugarBookBal() {
        return sugarBookBal;
    }

    public void setSugarBookBal(Integer sugarBookBal) {
        this.sugarBookBal = sugarBookBal;
    }

    public Integer getIdlyRavaGroundBal() {
        return idlyRavaGroundBal;
    }

    public void setIdlyRavaGroundBal(Integer idlyRavaGroundBal) {
        this.idlyRavaGroundBal = idlyRavaGroundBal;
    }

    public Integer getGGDalGroundBal() {
        return gGDalGroundBal;
    }

    public void setGGDalGroundBal(Integer gGDalGroundBal) {
        this.gGDalGroundBal = gGDalGroundBal;
    }

    public Integer getBagaraFlowerBookBal() {
        return bagaraFlowerBookBal;
    }

    public void setBagaraFlowerBookBal(Integer bagaraFlowerBookBal) {
        this.bagaraFlowerBookBal = bagaraFlowerBookBal;
    }

    public Integer getCopraGroundBal() {
        return copraGroundBal;
    }

    public void setCopraGroundBal(Integer copraGroundBal) {
        this.copraGroundBal = copraGroundBal;
    }

    public Integer getSplFruitGroundBal() {
        return splFruitGroundBal;
    }

    public void setSplFruitGroundBal(Integer splFruitGroundBal) {
        this.splFruitGroundBal = splFruitGroundBal;
    }

    public Integer getRiceBookBal() {
        return riceBookBal;
    }

    public void setRiceBookBal(Integer riceBookBal) {
        this.riceBookBal = riceBookBal;
    }

    public Integer getPotatoesGroundBal() {
        return potatoesGroundBal;
    }

    public void setPotatoesGroundBal(Integer potatoesGroundBal) {
        this.potatoesGroundBal = potatoesGroundBal;
    }

    public Integer getIlachiBookBal() {
        return ilachiBookBal;
    }

    public void setIlachiBookBal(Integer ilachiBookBal) {
        this.ilachiBookBal = ilachiBookBal;
    }

    public Double getRedChilliesBookBal() {
        return redChilliesBookBal;
    }

    public void setRedChilliesBookBal(Double redChilliesBookBal) {
        this.redChilliesBookBal = redChilliesBookBal;
    }

    public Integer getJaggeryBookBal() {
        return jaggeryBookBal;
    }

    public void setJaggeryBookBal(Integer jaggeryBookBal) {
        this.jaggeryBookBal = jaggeryBookBal;
    }

    public Integer getBagaraFlowerGroundBal() {
        return bagaraFlowerGroundBal;
    }

    public void setBagaraFlowerGroundBal(Integer bagaraFlowerGroundBal) {
        this.bagaraFlowerGroundBal = bagaraFlowerGroundBal;
    }

    public Integer getRagiCookieesGroundBal() {
        return ragiCookieesGroundBal;
    }

    public void setRagiCookieesGroundBal(Integer ragiCookieesGroundBal) {
        this.ragiCookieesGroundBal = ragiCookieesGroundBal;
    }

    public Integer getGasBookBal() {
        return gasBookBal;
    }

    public void setGasBookBal(Integer gasBookBal) {
        this.gasBookBal = gasBookBal;
    }

    public Integer getBajraBookBal() {
        return bajraBookBal;
    }

    public void setBajraBookBal(Integer bajraBookBal) {
        this.bajraBookBal = bajraBookBal;
    }

    public Integer getBaadamGroundBal() {
        return baadamGroundBal;
    }

    public void setBaadamGroundBal(Integer baadamGroundBal) {
        this.baadamGroundBal = baadamGroundBal;
    }
}
