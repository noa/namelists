# Wikidata Namelists

A Java program to download name lists from Wikidata using the
[Wikidata Toolkit](https://github.com/Wikidata/Wikidata-Toolkit). The
resulting lists contain both the entity names as well as known
aliases.

# Compilation

Via Maven:

```bash
mvn compile
```

# Usage

To get lists for the entity types listed in `wiki_types.txt` and the
languages listed in `wiki_lang.txt`, run:

```bash
$ mvn exec:java -Dexec.mainClass="edu.jhu.ListExtractor" -Dexec.args="-typePath wiki_types.txt -langPath wiki_lang.txt"
```

Note that before anything is downloaded, a Wikidata dump is downloaded
to the `dumpfiles` directory, which may take a while.

Optionally, to get names in *all languages* use the `-allLang` flag:

```bash
$ mvn exec:java -Dexec.mainClass="edu.jhu.ListExtractor" -Dexec.args="-typePath wiki_types.txt -allLang"
```

The results are placed under `results`, which will contain
subdirectories specific to each Wikidata dump. Each subdirectory will
contain `CODE_gazetteer.txt` and `CODE_gazetteer_counts.txt`, where
CODE is a Wikipedia language code.

# Sample results

Total counts for each language for the sample entity types in the provided `wiki_types.txt`:

```bash
$ find . -name "*_gazetteer.txt" | xargs wc --lines | sort -k1,1nr
 23140262 total
  3028286 ./results/wikidatawiki-20160222/en_gazetteer.txt
  2187537 ./results/wikidatawiki-20160222/fr_gazetteer.txt
  1864783 ./results/wikidatawiki-20160222/de_gazetteer.txt
  1609097 ./results/wikidatawiki-20160222/nl_gazetteer.txt
  1221877 ./results/wikidatawiki-20160222/es_gazetteer.txt
  1141332 ./results/wikidatawiki-20160222/sv_gazetteer.txt
  1082542 ./results/wikidatawiki-20160222/it_gazetteer.txt
   834776 ./results/wikidatawiki-20160222/nb_gazetteer.txt
   779046 ./results/wikidatawiki-20160222/da_gazetteer.txt
   747240 ./results/wikidatawiki-20160222/nn_gazetteer.txt
   540086 ./results/wikidatawiki-20160222/ru_gazetteer.txt
   432309 ./results/wikidatawiki-20160222/pl_gazetteer.txt
   350861 ./results/wikidatawiki-20160222/ja_gazetteer.txt
   349894 ./results/wikidatawiki-20160222/pt_gazetteer.txt
   276305 ./results/wikidatawiki-20160222/ca_gazetteer.txt
   226019 ./results/wikidatawiki-20160222/zh_gazetteer.txt
   224077 ./results/wikidatawiki-20160222/hu_gazetteer.txt
   221307 ./results/wikidatawiki-20160222/fi_gazetteer.txt
   215648 ./results/wikidatawiki-20160222/fa_gazetteer.txt
   215140 ./results/wikidatawiki-20160222/sh_gazetteer.txt
   184431 ./results/wikidatawiki-20160222/cs_gazetteer.txt
   151376 ./results/wikidatawiki-20160222/uk_gazetteer.txt
   150667 ./results/wikidatawiki-20160222/ar_gazetteer.txt
   140606 ./results/wikidatawiki-20160222/cy_gazetteer.txt
   136319 ./results/wikidatawiki-20160222/tr_gazetteer.txt
   130346 ./results/wikidatawiki-20160222/ro_gazetteer.txt
   113465 ./results/wikidatawiki-20160222/eo_gazetteer.txt
   111020 ./results/wikidatawiki-20160222/sr_gazetteer.txt
   108453 ./results/wikidatawiki-20160222/hr_gazetteer.txt
   106371 ./results/wikidatawiki-20160222/sl_gazetteer.txt
   103555 ./results/wikidatawiki-20160222/ko_gazetteer.txt
   103494 ./results/wikidatawiki-20160222/et_gazetteer.txt
    97343 ./results/wikidatawiki-20160222/sk_gazetteer.txt
    95749 ./results/wikidatawiki-20160222/ceb_gazetteer.txt
    93877 ./results/wikidatawiki-20160222/vi_gazetteer.txt
    92979 ./results/wikidatawiki-20160222/lt_gazetteer.txt
    89924 ./results/wikidatawiki-20160222/gl_gazetteer.txt
    84742 ./results/wikidatawiki-20160222/id_gazetteer.txt
    81778 ./results/wikidatawiki-20160222/eu_gazetteer.txt
    78556 ./results/wikidatawiki-20160222/he_gazetteer.txt
    72902 ./results/wikidatawiki-20160222/bg_gazetteer.txt
    69838 ./results/wikidatawiki-20160222/ms_gazetteer.txt
    63000 ./results/wikidatawiki-20160222/af_gazetteer.txt
    62186 ./results/wikidatawiki-20160222/nds_gazetteer.txt
    62169 ./results/wikidatawiki-20160222/br_gazetteer.txt
    61346 ./results/wikidatawiki-20160222/oc_gazetteer.txt
    59608 ./results/wikidatawiki-20160222/zh-hans_gazetteer.txt
    59450 ./results/wikidatawiki-20160222/zh-hant_gazetteer.txt
    59118 ./results/wikidatawiki-20160222/rm_gazetteer.txt
    58994 ./results/wikidatawiki-20160222/lb_gazetteer.txt
    58459 ./results/wikidatawiki-20160222/en-gb_gazetteer.txt
    58313 ./results/wikidatawiki-20160222/ga_gazetteer.txt
    57906 ./results/wikidatawiki-20160222/is_gazetteer.txt
    57832 ./results/wikidatawiki-20160222/zh-hk_gazetteer.txt
    56571 ./results/wikidatawiki-20160222/gsw_gazetteer.txt
    55576 ./results/wikidatawiki-20160222/en-ca_gazetteer.txt
    55548 ./results/wikidatawiki-20160222/mg_gazetteer.txt
    54469 ./results/wikidatawiki-20160222/sco_gazetteer.txt
    51970 ./results/wikidatawiki-20160222/ast_gazetteer.txt
    51511 ./results/wikidatawiki-20160222/an_gazetteer.txt
    51425 ./results/wikidatawiki-20160222/pms_gazetteer.txt
    50776 ./results/wikidatawiki-20160222/el_gazetteer.txt
    48902 ./results/wikidatawiki-20160222/sq_gazetteer.txt
    47275 ./results/wikidatawiki-20160222/wa_gazetteer.txt
    46525 ./results/wikidatawiki-20160222/gd_gazetteer.txt
    45426 ./results/wikidatawiki-20160222/vec_gazetteer.txt
    45163 ./results/wikidatawiki-20160222/nds-nl_gazetteer.txt
    44803 ./results/wikidatawiki-20160222/scn_gazetteer.txt
    44722 ./results/wikidatawiki-20160222/bs_gazetteer.txt
    43945 ./results/wikidatawiki-20160222/bar_gazetteer.txt
    42894 ./results/wikidatawiki-20160222/li_gazetteer.txt
    42754 ./results/wikidatawiki-20160222/sc_gazetteer.txt
    42627 ./results/wikidatawiki-20160222/co_gazetteer.txt
    42511 ./results/wikidatawiki-20160222/vls_gazetteer.txt
    41599 ./results/wikidatawiki-20160222/pcd_gazetteer.txt
    41484 ./results/wikidatawiki-20160222/nap_gazetteer.txt
    41370 ./results/wikidatawiki-20160222/lij_gazetteer.txt
    41306 ./results/wikidatawiki-20160222/hy_gazetteer.txt
    40735 ./results/wikidatawiki-20160222/fur_gazetteer.txt
    36582 ./results/wikidatawiki-20160222/sr-el_gazetteer.txt
    35661 ./results/wikidatawiki-20160222/az_gazetteer.txt
    33586 ./results/wikidatawiki-20160222/lmo_gazetteer.txt
    33315 ./results/wikidatawiki-20160222/la_gazetteer.txt
    31448 ./results/wikidatawiki-20160222/lv_gazetteer.txt
    31318 ./results/wikidatawiki-20160222/vo_gazetteer.txt
    30986 ./results/wikidatawiki-20160222/pt-br_gazetteer.txt
    30182 ./results/wikidatawiki-20160222/de-ch_gazetteer.txt
    30032 ./results/wikidatawiki-20160222/be_gazetteer.txt
    28699 ./results/wikidatawiki-20160222/io_gazetteer.txt
    27513 ./results/wikidatawiki-20160222/ht_gazetteer.txt
    26246 ./results/wikidatawiki-20160222/ur_gazetteer.txt
    25830 ./results/wikidatawiki-20160222/ta_gazetteer.txt
    24872 ./results/wikidatawiki-20160222/zh-cn_gazetteer.txt
    24847 ./results/wikidatawiki-20160222/zh-tw_gazetteer.txt
    24701 ./results/wikidatawiki-20160222/ka_gazetteer.txt
    24282 ./results/wikidatawiki-20160222/th_gazetteer.txt
    22865 ./results/wikidatawiki-20160222/sw_gazetteer.txt
    22420 ./results/wikidatawiki-20160222/my_gazetteer.txt
    21791 ./results/wikidatawiki-20160222/zh-sg_gazetteer.txt
    21481 ./results/wikidatawiki-20160222/sr-ec_gazetteer.txt
    20648 ./results/wikidatawiki-20160222/kk_gazetteer.txt
    19677 ./results/wikidatawiki-20160222/hi_gazetteer.txt
    18916 ./results/wikidatawiki-20160222/tl_gazetteer.txt
    18154 ./results/wikidatawiki-20160222/mk_gazetteer.txt
    17651 ./results/wikidatawiki-20160222/ia_gazetteer.txt
    17231 ./results/wikidatawiki-20160222/min_gazetteer.txt
    17042 ./results/wikidatawiki-20160222/ie_gazetteer.txt
    16406 ./results/wikidatawiki-20160222/nrm_gazetteer.txt
    16360 ./results/wikidatawiki-20160222/uz_gazetteer.txt
    16358 ./results/wikidatawiki-20160222/frp_gazetteer.txt
    16273 ./results/wikidatawiki-20160222/wo_gazetteer.txt
    16208 ./results/wikidatawiki-20160222/zu_gazetteer.txt
    16046 ./results/wikidatawiki-20160222/kg_gazetteer.txt
    15406 ./results/wikidatawiki-20160222/nan_gazetteer.txt
    15133 ./results/wikidatawiki-20160222/bn_gazetteer.txt
    14997 ./results/wikidatawiki-20160222/de-at_gazetteer.txt
    14446 ./results/wikidatawiki-20160222/mr_gazetteer.txt
    14437 ./results/wikidatawiki-20160222/be-tarask_gazetteer.txt
    12893 ./results/wikidatawiki-20160222/ml_gazetteer.txt
    12579 ./results/wikidatawiki-20160222/tg_gazetteer.txt
    12053 ./results/wikidatawiki-20160222/fy_gazetteer.txt
    11405 ./results/wikidatawiki-20160222/vmf_gazetteer.txt
    11340 ./results/wikidatawiki-20160222/war_gazetteer.txt
    10210 ./results/wikidatawiki-20160222/new_gazetteer.txt
     9485 ./results/wikidatawiki-20160222/yue_gazetteer.txt
     8451 ./results/wikidatawiki-20160222/jv_gazetteer.txt
     8450 ./results/wikidatawiki-20160222/kab_gazetteer.txt
     8133 ./results/wikidatawiki-20160222/pap_gazetteer.txt
     7859 ./results/wikidatawiki-20160222/yo_gazetteer.txt
     7677 ./results/wikidatawiki-20160222/bm_gazetteer.txt
     7666 ./results/wikidatawiki-20160222/frc_gazetteer.txt
     7500 ./results/wikidatawiki-20160222/ce_gazetteer.txt
     7497 ./results/wikidatawiki-20160222/jam_gazetteer.txt
     7488 ./results/wikidatawiki-20160222/rgn_gazetteer.txt
     7487 ./results/wikidatawiki-20160222/prg_gazetteer.txt
     7172 ./results/wikidatawiki-20160222/pnb_gazetteer.txt
     6836 ./results/wikidatawiki-20160222/mn_gazetteer.txt
     6441 ./results/wikidatawiki-20160222/te_gazetteer.txt
     6386 ./results/wikidatawiki-20160222/arz_gazetteer.txt
     6180 ./results/wikidatawiki-20160222/kk-cyrl_gazetteer.txt
     6163 ./results/wikidatawiki-20160222/ky_gazetteer.txt
     6116 ./results/wikidatawiki-20160222/kk-arab_gazetteer.txt
     6114 ./results/wikidatawiki-20160222/kk-latn_gazetteer.txt
     5911 ./results/wikidatawiki-20160222/pa_gazetteer.txt
     5702 ./results/wikidatawiki-20160222/fo_gazetteer.txt
     5666 ./results/wikidatawiki-20160222/bpy_gazetteer.txt
     5294 ./results/wikidatawiki-20160222/ne_gazetteer.txt
     5156 ./results/wikidatawiki-20160222/ckb_gazetteer.txt
     4935 ./results/wikidatawiki-20160222/qu_gazetteer.txt
     4762 ./results/wikidatawiki-20160222/tt_gazetteer.txt
     4230 ./results/wikidatawiki-20160222/simple_gazetteer.txt
     3974 ./results/wikidatawiki-20160222/ku_gazetteer.txt
     3877 ./results/wikidatawiki-20160222/cv_gazetteer.txt
     3858 ./results/wikidatawiki-20160222/kn_gazetteer.txt
     3841 ./results/wikidatawiki-20160222/hif_gazetteer.txt
     3468 ./results/wikidatawiki-20160222/or_gazetteer.txt
     3461 ./results/wikidatawiki-20160222/yi_gazetteer.txt
     3415 ./results/wikidatawiki-20160222/diq_gazetteer.txt
     3250 ./results/wikidatawiki-20160222/hsb_gazetteer.txt
     3138 ./results/wikidatawiki-20160222/zh-mo_gazetteer.txt
     3054 ./results/wikidatawiki-20160222/sa_gazetteer.txt
     3037 ./results/wikidatawiki-20160222/zh-my_gazetteer.txt
     2915 ./results/wikidatawiki-20160222/gu_gazetteer.txt
     2828 ./results/wikidatawiki-20160222/mzn_gazetteer.txt
     2584 ./results/wikidatawiki-20160222/nah_gazetteer.txt
     2500 ./results/wikidatawiki-20160222/kl_gazetteer.txt
     2323 ./results/wikidatawiki-20160222/os_gazetteer.txt
     2311 ./results/wikidatawiki-20160222/ba_gazetteer.txt
     2282 ./results/wikidatawiki-20160222/eml_gazetteer.txt
     2266 ./results/wikidatawiki-20160222/sgs_gazetteer.txt
     2141 ./results/wikidatawiki-20160222/pam_gazetteer.txt
     2045 ./results/wikidatawiki-20160222/fit_gazetteer.txt
     1961 ./results/wikidatawiki-20160222/vep_gazetteer.txt
     1960 ./results/wikidatawiki-20160222/si_gazetteer.txt
     1909 ./results/wikidatawiki-20160222/ilo_gazetteer.txt
     1884 ./results/wikidatawiki-20160222/mt_gazetteer.txt
     1851 ./results/wikidatawiki-20160222/szl_gazetteer.txt
     1808 ./results/wikidatawiki-20160222/lzh_gazetteer.txt
     1793 ./results/wikidatawiki-20160222/dsb_gazetteer.txt
     1713 ./results/wikidatawiki-20160222/su_gazetteer.txt
     1627 ./results/wikidatawiki-20160222/am_gazetteer.txt
     1601 ./results/wikidatawiki-20160222/sah_gazetteer.txt
     1556 ./results/wikidatawiki-20160222/rup_gazetteer.txt
     1545 ./results/wikidatawiki-20160222/xmf_gazetteer.txt
     1451 ./results/wikidatawiki-20160222/wuu_gazetteer.txt
     1433 ./results/wikidatawiki-20160222/ps_gazetteer.txt
     1396 ./results/wikidatawiki-20160222/gv_gazetteer.txt
     1387 ./results/wikidatawiki-20160222/ksh_gazetteer.txt
     1367 ./results/wikidatawiki-20160222/stq_gazetteer.txt
     1359 ./results/wikidatawiki-20160222/frr_gazetteer.txt
     1358 ./results/wikidatawiki-20160222/zea_gazetteer.txt
     1349 ./results/wikidatawiki-20160222/azb_gazetteer.txt
     1343 ./results/wikidatawiki-20160222/bcl_gazetteer.txt
     1325 ./results/wikidatawiki-20160222/kw_gazetteer.txt
     1310 ./results/wikidatawiki-20160222/kk-kz_gazetteer.txt
     1306 ./results/wikidatawiki-20160222/kk-tr_gazetteer.txt
     1305 ./results/wikidatawiki-20160222/kk-cn_gazetteer.txt
     1300 ./results/wikidatawiki-20160222/ku-latn_gazetteer.txt
     1289 ./results/wikidatawiki-20160222/ext_gazetteer.txt
     1278 ./results/wikidatawiki-20160222/mwl_gazetteer.txt
     1273 ./results/wikidatawiki-20160222/ku-arab_gazetteer.txt
     1242 ./results/wikidatawiki-20160222/so_gazetteer.txt
     1201 ./results/wikidatawiki-20160222/pdc_gazetteer.txt
     1164 ./results/wikidatawiki-20160222/mrj_gazetteer.txt
     1163 ./results/wikidatawiki-20160222/ang_gazetteer.txt
     1150 ./results/wikidatawiki-20160222/se_gazetteer.txt
     1130 ./results/wikidatawiki-20160222/hak_gazetteer.txt
     1080 ./results/wikidatawiki-20160222/ace_gazetteer.txt
     1078 ./results/wikidatawiki-20160222/as_gazetteer.txt
     1021 ./results/wikidatawiki-20160222/lad_gazetteer.txt
     1012 ./results/wikidatawiki-20160222/no_gazetteer.txt
      997 ./results/wikidatawiki-20160222/bo_gazetteer.txt
      943 ./results/wikidatawiki-20160222/csb_gazetteer.txt
      904 ./results/wikidatawiki-20160222/tk_gazetteer.txt
      881 ./results/wikidatawiki-20160222/mhr_gazetteer.txt
      875 ./results/wikidatawiki-20160222/nov_gazetteer.txt
      857 ./results/wikidatawiki-20160222/kaa_gazetteer.txt
      839 ./results/wikidatawiki-20160222/gan_gazetteer.txt
      838 ./results/wikidatawiki-20160222/na_gazetteer.txt
      838 ./results/wikidatawiki-20160222/vro_gazetteer.txt
      831 ./results/wikidatawiki-20160222/pfl_gazetteer.txt
      804 ./results/wikidatawiki-20160222/tg-latn_gazetteer.txt
      786 ./results/wikidatawiki-20160222/ha_gazetteer.txt
      753 ./results/wikidatawiki-20160222/cdo_gazetteer.txt
      749 ./results/wikidatawiki-20160222/mi_gazetteer.txt
      740 ./results/wikidatawiki-20160222/lez_gazetteer.txt
      732 ./results/wikidatawiki-20160222/gn_gazetteer.txt
      726 ./results/wikidatawiki-20160222/ug_gazetteer.txt
      701 ./results/wikidatawiki-20160222/xal_gazetteer.txt
      673 ./results/wikidatawiki-20160222/ay_gazetteer.txt
      673 ./results/wikidatawiki-20160222/gag_gazetteer.txt
      670 ./results/wikidatawiki-20160222/cbk-zam_gazetteer.txt
      644 ./results/wikidatawiki-20160222/crh-latn_gazetteer.txt
      642 ./results/wikidatawiki-20160222/bh_gazetteer.txt
      638 ./results/wikidatawiki-20160222/rw_gazetteer.txt
      619 ./results/wikidatawiki-20160222/rue_gazetteer.txt
      608 ./results/wikidatawiki-20160222/km_gazetteer.txt
      599 ./results/wikidatawiki-20160222/udm_gazetteer.txt
      583 ./results/wikidatawiki-20160222/pi_gazetteer.txt
      573 ./results/wikidatawiki-20160222/bxr_gazetteer.txt
      573 ./results/wikidatawiki-20160222/ln_gazetteer.txt
      568 ./results/wikidatawiki-20160222/roa-tara_gazetteer.txt
      561 ./results/wikidatawiki-20160222/dv_gazetteer.txt
      539 ./results/wikidatawiki-20160222/tet_gazetteer.txt
      533 ./results/wikidatawiki-20160222/ty_gazetteer.txt
      527 ./results/wikidatawiki-20160222/sn_gazetteer.txt
      517 ./results/wikidatawiki-20160222/haw_gazetteer.txt
      512 ./results/wikidatawiki-20160222/mai_gazetteer.txt
      496 ./results/wikidatawiki-20160222/grc_gazetteer.txt
      487 ./results/wikidatawiki-20160222/pag_gazetteer.txt
      478 ./results/wikidatawiki-20160222/mo_gazetteer.txt
      476 ./results/wikidatawiki-20160222/ig_gazetteer.txt
      471 ./results/wikidatawiki-20160222/kv_gazetteer.txt
      459 ./results/wikidatawiki-20160222/av_gazetteer.txt
      433 ./results/wikidatawiki-20160222/tpi_gazetteer.txt
      395 ./results/wikidatawiki-20160222/arc_gazetteer.txt
      382 ./results/wikidatawiki-20160222/tw_gazetteer.txt
      376 ./results/wikidatawiki-20160222/pih_gazetteer.txt
      370 ./results/wikidatawiki-20160222/lrc_gazetteer.txt
      340 ./results/wikidatawiki-20160222/sd_gazetteer.txt
      336 ./results/wikidatawiki-20160222/ab_gazetteer.txt
      323 ./results/wikidatawiki-20160222/jbo_gazetteer.txt
      320 ./results/wikidatawiki-20160222/kbd_gazetteer.txt
      313 ./results/wikidatawiki-20160222/gan-hans_gazetteer.txt
      311 ./results/wikidatawiki-20160222/srn_gazetteer.txt
      310 ./results/wikidatawiki-20160222/tyv_gazetteer.txt
      304 ./results/wikidatawiki-20160222/ak_gazetteer.txt
      302 ./results/wikidatawiki-20160222/nv_gazetteer.txt
      300 ./results/wikidatawiki-20160222/gan-hant_gazetteer.txt
      300 ./results/wikidatawiki-20160222/om_gazetteer.txt
      291 ./results/wikidatawiki-20160222/za_gazetteer.txt
      290 ./results/wikidatawiki-20160222/bjn_gazetteer.txt
      290 ./results/wikidatawiki-20160222/cu_gazetteer.txt
      286 ./results/wikidatawiki-20160222/bi_gazetteer.txt
      271 ./results/wikidatawiki-20160222/gom_gazetteer.txt
      270 ./results/wikidatawiki-20160222/ch_gazetteer.txt
      269 ./results/wikidatawiki-20160222/ss_gazetteer.txt
      262 ./results/wikidatawiki-20160222/ki_gazetteer.txt
      258 ./results/wikidatawiki-20160222/koi_gazetteer.txt
      257 ./results/wikidatawiki-20160222/lo_gazetteer.txt
      247 ./results/wikidatawiki-20160222/ee_gazetteer.txt
      239 ./results/wikidatawiki-20160222/krc_gazetteer.txt
      239 ./results/wikidatawiki-20160222/myv_gazetteer.txt
      237 ./results/wikidatawiki-20160222/bho_gazetteer.txt
      236 ./results/wikidatawiki-20160222/got_gazetteer.txt
      235 ./results/wikidatawiki-20160222/glk_gazetteer.txt
      235 ./results/wikidatawiki-20160222/map-bms_gazetteer.txt
      234 ./results/wikidatawiki-20160222/als_gazetteer.txt
      233 ./results/wikidatawiki-20160222/ltg_gazetteer.txt
      222 ./results/wikidatawiki-20160222/mdf_gazetteer.txt
      209 ./results/wikidatawiki-20160222/pdt_gazetteer.txt
      204 ./results/wikidatawiki-20160222/rn_gazetteer.txt
      200 ./results/wikidatawiki-20160222/aa_gazetteer.txt
      198 ./results/wikidatawiki-20160222/st_gazetteer.txt
      193 ./results/wikidatawiki-20160222/tum_gazetteer.txt
      191 ./results/wikidatawiki-20160222/rmy_gazetteer.txt
      189 ./results/wikidatawiki-20160222/sg_gazetteer.txt
      186 ./results/wikidatawiki-20160222/tokipona_gazetteer.txt
      175 ./results/wikidatawiki-20160222/be-x-old_gazetteer.txt
      171 ./results/wikidatawiki-20160222/lbe_gazetteer.txt
      168 ./results/wikidatawiki-20160222/pnt_gazetteer.txt
      164 ./results/wikidatawiki-20160222/sm_gazetteer.txt
      162 ./results/wikidatawiki-20160222/chy_gazetteer.txt
      162 ./results/wikidatawiki-20160222/iu_gazetteer.txt
      154 ./results/wikidatawiki-20160222/xh_gazetteer.txt
      149 ./results/wikidatawiki-20160222/chr_gazetteer.txt
      147 ./results/wikidatawiki-20160222/nso_gazetteer.txt
      142 ./results/wikidatawiki-20160222/tn_gazetteer.txt
      138 ./results/wikidatawiki-20160222/ff_gazetteer.txt
      129 ./results/wikidatawiki-20160222/to_gazetteer.txt
      128 ./results/wikidatawiki-20160222/ks_gazetteer.txt
      127 ./results/wikidatawiki-20160222/ts_gazetteer.txt
      120 ./results/wikidatawiki-20160222/dz_gazetteer.txt
      110 ./results/wikidatawiki-20160222/lg_gazetteer.txt
      109 ./results/wikidatawiki-20160222/bug_gazetteer.txt
      104 ./results/wikidatawiki-20160222/brh_gazetteer.txt
      103 ./results/wikidatawiki-20160222/ny_gazetteer.txt
```
