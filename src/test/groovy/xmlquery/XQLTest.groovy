package xmlquery

import org.junit.Before
import org.junit.Test
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input

import static org.junit.Assert.assertTrue

class XQLTest {

    static stringXML = """<issd_project_farmer_registration_form_v1 formKey="issd_project_farmer_registration_form_v1" id="9" name="Farmer Registration form">
  <lsb_search>
    <lsb></lsb>
    <name>Nyim ber LSB</name>
    <__code>NLA60105</__code>
    <partner>Gulu Agricultural Development Company</partner>
    <zone>northern</zone>
    <district>lamwo</district>
    <subcounty>lukung</subcounty>
  </lsb_search>
  <farmer_details>
    <name_farmer>Okia Albert</name_farmer>
    <sex>female</sex>
    <age>1968-01-01</age>
  </farmer_details>
</issd_project_farmer_registration_form_v1>
        """

    static String xmlWithRepeats = """<issd_project_planting_returns_form_v1 id="13" name="Planting Returns Form">
<instruction/>
<season>_2019a</season>
<crop>cassava</crop>
<variety_cassava>narocase_1</variety_cassava>
<seed_class>foundation_seed</seed_class>
<planting_returns>
<farmer/>
<farmer_name>Owiny Bruno</farmer_name>
<__code>WPK6063/0003</__code>
<lsb_name>Pokwero</lsb_name>
<partner>WENIPS</partner>
<district>pakwach</district>
<subcounty>panyango</subcounty>
<zone>westnile</zone>
<arceage>1.0</arceage>
<quantity_planted_bags>6.0</quantity_planted_bags>
<planting_date>2019-04-06</planting_date>
</planting_returns>
<planting_returns>
<farmer/>
<farmer_name>Alli Quirino</farmer_name>
<__code>WPK6063/0006</__code>
<lsb_name>Pokwero</lsb_name>
<partner>WENIPS</partner>
<district>pakwach</district>
<subcounty>panyango</subcounty>
<zone>westnile</zone>
<arceage>1.0</arceage>
<quantity_planted_bags>6.0</quantity_planted_bags>
<planting_date>2019-04-09</planting_date>
</planting_returns>
<planting_returns>
<farmer/>
<farmer_name>BLOCK_Pokwero</farmer_name>
<__code>WPK6063/0046</__code>
<lsb_name>Pokwero</lsb_name>
<partner>WENIPS</partner>
<district>pakwach</district>
<subcounty>panyango</subcounty>
<zone>westnile</zone>
<arceage>8.0</arceage>
<quantity_planted_bags>48.0</quantity_planted_bags>
<planting_date>2019-04-03</planting_date>
</planting_returns>
<date>2019-04-30 01:24:51 PM</date>
<unique_id>uuid:30263fff-af7e-4443-a048-d1cc7a178afe</unique_id>
</issd_project_planting_returns_form_v1>"""


    static String sampleXml3 = """<iics_survey_school_questionnaire_v1>
                                <visit>baseline</visit>
                                    <school_prompt>
                                    <school_name>null</school_name>
                                    <__code>MAS/KIM/0002</__code>
                                </school_prompt>
                        </iics_survey_school_questionnaire_v1>"""
    static String sampleXml4 = """<?xml version="1.0" encoding="UTF-8"?><issd_project_farmer_registration_form_v1 formKey="issd_project_farmer_registration_form_v1" id="9" name="Farmer Registration form">
  <lsb_search>
    <lsb/>
    <name>Amatura Cooperative</name>
    <lsb_code>WMO6039</lsb_code>
    <partner>CEFORD</partner>
    <district>buhweju</district>
    <subcounty>bihanga</subcounty>
  </lsb_search>
  <farmer_details>
    <farmer_name>AMBAYO JOACHIM</farmer_name>
    <sex>male</sex>
    <age>1964-01-01</age>
  </farmer_details>
  <zone_farmer>westnile</zone_farmer>
  <district_farmer>moyo</district_farmer>
  <subcountry_farmer>moyo</subcountry_farmer>
  <parish>Vurra</parish>
  <village>Vurra opi</village>
  <type_farmer>general_farmer</type_farmer>
  <date>2018-07-30 03:14:14 PM</date>
  <unique_id>uuid:6d4fa225-02da-4af7-a91e-85cc41494719</unique_id>
  <farmerid2/>
  <meta>
    <__start>2018-07-30T15:14:14.483EAT</__start>
    <__end>2018-07-30T15:14:14.483EAT</__end>
    <__today>2018-07-30</__today>
    <instanceID>uuid:aebe3d89-9bce-440d-a912-9afd3fdaf2be</instanceID>
    <__reviewed/>
    <__reviewedBy/>
  </meta>
</issd_project_farmer_registration_form_v1>
                                """

    @Before
    void setUp() {
//
    }

    @Test
    void testUpdate() {
        def xql = new XQL('lsb_search')
                .update(stringXML)
                .set('lsb')
                .to('Kakavii')
                .where('__code')
                .isEqualTo('NLA60105')

        def newxml = xql.queryKeepNameSpaces()

        def expected = """<issd_project_farmer_registration_form_v1 formKey="issd_project_farmer_registration_form_v1" id="9" name="Farmer Registration form">
<lsb_search>
<lsb>
Kakavii
</lsb>
<name>
Nyim ber LSB
</name>
<__code>
NLA60105
</__code>
<partner>
Gulu Agricultural Development Company
</partner>
<zone>
northern
</zone>
<district>
lamwo
</district>
<subcounty>
lukung
</subcounty>
</lsb_search>
<farmer_details>
<name_farmer>
Okia Albert
</name_farmer>
<sex>
female
</sex>
<age>
1968-01-01
</age>
</farmer_details>
</issd_project_farmer_registration_form_v1>
"""

        assertTrue isSameXml(newxml, expected)
    }

    @Test
    void testUpdateFormDataWithRepeats() {

        def expected = """<issd_project_planting_returns_form_v1 id="13" name="Planting Returns Form">
<instruction/>
<season>_2019a</season>
<crop>cassava</crop>
<variety_cassava>narocase_1</variety_cassava>
<seed_class>foundation_seed</seed_class>
<planting_returns>
<farmer/>
<farmer_name>Owiny Bruno</farmer_name>
<__code>WPK6063/0003</__code>
<lsb_name>Pokwero</lsb_name>
<partner>WENIPS</partner>
<district>kakabouy</district>
<subcounty>panyango</subcounty>
<zone>westnile</zone>
<arceage>1.0</arceage>
<quantity_planted_bags>6.0</quantity_planted_bags>
<planting_date>2019-04-06</planting_date>
</planting_returns>
<planting_returns>
<farmer/>
<farmer_name>Alli Quirino</farmer_name>
<__code>WPK6063/0006</__code>
<lsb_name>Pokwero</lsb_name>
<partner>WENIPS</partner>
<district>pakwach</district>
<subcounty>panyango</subcounty>
<zone>westnile</zone>
<arceage>1.0</arceage>
<quantity_planted_bags>6.0</quantity_planted_bags>
<planting_date>2019-04-09</planting_date>
</planting_returns>
<planting_returns>
<farmer/>
<farmer_name>BLOCK_Pokwero</farmer_name>
<__code>WPK6063/0046</__code>
<lsb_name>Pokwero</lsb_name>
<partner>WENIPS</partner>
<district>pakwach</district>
<subcounty>panyango</subcounty>
<zone>westnile</zone>
<arceage>8.0</arceage>
<quantity_planted_bags>48.0</quantity_planted_bags>
<planting_date>2019-04-03</planting_date>
</planting_returns>
<date>2019-04-30 01:24:51 PM</date>
<unique_id>uuid:30263fff-af7e-4443-a048-d1cc7a178afe</unique_id>
</issd_project_planting_returns_form_v1>"""

        def xql = new XQL('planting_returns')
                .update(xmlWithRepeats.trim().replaceAll("\n", ""))
                .set('district')
                .to('kakabouy')
                .where('__code')
                .isEqualTo('WPK6063/0003')
        def newxml = xql.queryKeepNameSpaces()

        assertTrue isSameXml(newxml, expected)
    }


    @Test
    void testUpdateFormDataWithRepeatsOn2ndRepeat() {

        def expected = """<issd_project_planting_returns_form_v1 id="13" name="Planting Returns Form">
<instruction/>
<season>_2019a</season>
<crop>cassava</crop>
<variety_cassava>narocase_1</variety_cassava>
<seed_class>foundation_seed</seed_class>
<planting_returns>
<farmer/>
<farmer_name>Owiny Bruno</farmer_name>
<__code>WPK6063/0003</__code>
<lsb_name>Pokwero</lsb_name>
<partner>WENIPS</partner>
<district>pakwach</district>
<subcounty>panyango</subcounty>
<zone>westnile</zone>
<arceage>1.0</arceage>
<quantity_planted_bags>6.0</quantity_planted_bags>
<planting_date>2019-04-06</planting_date>
</planting_returns>
<planting_returns>
<farmer/>
<farmer_name>Alli Quirino</farmer_name>
<__code>WPK6063/0006</__code>
<lsb_name>Pokwero</lsb_name>
<partner>WENIPS</partner>
<district>kakabouy</district>
<subcounty>panyango</subcounty>
<zone>westnile</zone>
<arceage>1.0</arceage>
<quantity_planted_bags>6.0</quantity_planted_bags>
<planting_date>2019-04-09</planting_date>
</planting_returns>
<planting_returns>
<farmer/>
<farmer_name>BLOCK_Pokwero</farmer_name>
<__code>WPK6063/0046</__code>
<lsb_name>Pokwero</lsb_name>
<partner>WENIPS</partner>
<district>pakwach</district>
<subcounty>panyango</subcounty>
<zone>westnile</zone>
<arceage>8.0</arceage>
<quantity_planted_bags>48.0</quantity_planted_bags>
<planting_date>2019-04-03</planting_date>
</planting_returns>
<date>2019-04-30 01:24:51 PM</date>
<unique_id>uuid:30263fff-af7e-4443-a048-d1cc7a178afe</unique_id>
</issd_project_planting_returns_form_v1>"""

        def xql = new XQL('planting_returns')
                .update(xmlWithRepeats.trim().replaceAll("\n", ""))
                .set('district')
                .to('kakabouy')
                .where('__code')
                .isEqualTo('WPK6063/0006')
        def newxml = xql.queryKeepNameSpaces()

        assertTrue isSameXml(newxml, expected)
    }

    @Test
    void testUpdateDoesntDeformXML() {
        def cleanXml = """<people><person><name>kaka</name></person></people>"""

        def expected = """<people><person><name>martin</name></person></people>"""
        def xql = new XQL()
                .update(cleanXml.trim())
                .set('name')
                .to('martin')
                .where('name')
                .isEqualTo('kaka')
        def newxml = xql.queryAsOneString()
        println newxml

        assertTrue isSameXml(newxml, expected)
    }

    @Test
    void testUpdateKeepsTagAndValueOnOneLine() {
        def xmldata = """<?xml version="1.0" encoding="UTF-8"?><issd_study_lsb_profile_form_v1 xmlns:jr="http://openrosa.org/javarosa" id="3" name="LSB Profile Form" formKey="issd_study_lsb_profile_form_v1">
<zone>westnile</zone>
<district>zomboTestUpdate</district>
<sub_county>warr</sub_county>
<name_lsb>Agiermach Ogiebu Women Group for Development</name_lsb>
<category>farmer_group</category>
<contact_person>Anna Odupi</contact_person>
<contact>0771884328</contact>
<crop_grown>beans  irish_potato</crop_grown>
<organisation_name>ISSD Uganda</organisation_name>
<contact_organisation>Adong Joyce Christine</contact_organisation>
<contact_organisatio>0755049180</contact_organisatio>
<maaif_registered>no</maaif_registered>
<maaif_certificate/>
<__code>WZO6001</__code>
<status>active</status>
<year>2012-01-01</year>
<location/>
<picture/>
<date>2018-07-25 03:30:34 PM</date>
<unique_id>uuid:6b40cc63-c398-403d-a974-68870a82e612</unique_id>

</issd_study_lsb_profile_form_v1>"""

        def expected = """<?xml version="1.0" encoding="UTF-8"?><issd_study_lsb_profile_form_v1 xmlns:jr="http://openrosa.org/javarosa" id="3" name="LSB Profile Form" formKey="issd_study_lsb_profile_form_v1">
<zone>westnile</zone>
<district>zomboTestUpdate2</district>
<sub_county>warr</sub_county>
<name_lsb>Agiermach Ogiebu Women Group for Development</name_lsb>
<category>farmer_group</category>
<contact_person>Anna Odupi</contact_person>
<contact>0771884328</contact>
<crop_grown>beans  irish_potato</crop_grown>
<organisation_name>ISSD Uganda</organisation_name>
<contact_organisation>Adong Joyce Christine</contact_organisation>
<contact_organisatio>0755049180</contact_organisatio>
<maaif_registered>no</maaif_registered>
<maaif_certificate/>
<__code>WZO6001</__code>
<status>active</status>
<year>2012-01-01</year>
<location/>
<picture/>
<date>2018-07-25 03:30:34 PM</date>
<unique_id>uuid:6b40cc63-c398-403d-a974-68870a82e612</unique_id>

</issd_study_lsb_profile_form_v1>"""

        def xql = new XQL()
                .update(xmldata.trim())
                .set('district')
                .to('zomboTestUpdate2')
                .where('__code')
                .isEqualTo('WZO6001')
        def newxml = xql.queryAsOneString()
        println newxml

        assertTrue isSameXml(newxml, expected)
    }

    boolean isSameXml(String xml1, String xml2) {
        def build = DiffBuilder.compare(Input.fromString(xml1))
                .withTest(Input.fromString(xml2))
                .ignoreWhitespace()
                .ignoreComments()
                .normalizeWhitespace()
                .build()
        return !build.hasDifferences()
    }

    @Test
    void testQuerySelect() {
        def cleanXml = """<iics_survey_school_questionnaire_v1>
                                <visit>baseline</visit>
                                    <school_prompt>
                                    <school_name>null</school_name>
                                    <__code>MAS/KIM/0002</__code>
                                </school_prompt>
                        </iics_survey_school_questionnaire_v1>"""

        def xql = new XQL()
                .select("__code")
                .from(cleanXml)
                .where("__code")
                .isEqualTo("MAS/KIM/0002")
                .selectWhere()
        assertTrue "MAS/KIM/0002" == xql

    }

    @Test
    void testQueryWithoutWhereClause() {
        def cleanXml = """<iics_survey_school_questionnaire_v1>
                                <visit>baseline</visit>
                                    <school_prompt>
                                    <school_name>null</school_name>
                                    <__code>MAS/KIM/0002</__code>
                                </school_prompt>
                        </iics_survey_school_questionnaire_v1>"""

        def xql = new XQL()
                .select("__code")
                .from(cleanXml)
                .selectValue()
        assertTrue "MAS/KIM/0002" == xql
    }

    @Test
    void testAlterXMLAdd(){
        def newXml = """<?xml version="1.0" encoding="UTF-8"?>
                        <iics_survey_school_questionnaire_v1>
                                <visit>baseline</visit>
                                    <school_prompt>
                                        <school_name>null</school_name>
                                        <__code>MAS/KIM/0002</__code>
                                    </school_prompt>
                                <groupCode>FG12</groupCode>
                        </iics_survey_school_questionnaire_v1>"""
        def result = new XQL()
                .alterXML(sampleXml3)
                .add("groupCode")
                .withDefaultVal("FG12")
                .execute()

        assertTrue isSameXml(newXml,result)
    }
    @Test
    void testAlterXMLInsertBefore() {
        def newXml = """<?xml version="1.0" encoding="UTF-8"?>
                        <iics_survey_school_questionnaire_v1>
                                <groupCode>FG12</groupCode>
                                <visit>baseline</visit>
                                    <school_prompt>
                                        <school_name>null</school_name>
                                        <__code>MAS/KIM/0002</__code>
                                    </school_prompt>
                        </iics_survey_school_questionnaire_v1>"""
        def result = new XQL()
                .alterXML(sampleXml3)
                .add("groupCode")
                .withDefaultVal("FG12")
                .insertBefore("visit")

        assertTrue isSameXml(newXml,result)

    }

    @Test
    void testAlterXMLInsertBeforeWithGroup(){
        def newXml = """<?xml version="1.0" encoding="UTF-8"?>
                        <iics_survey_school_questionnaire_v1>
                                <visit>baseline</visit>
                                    <school_prompt>
                                        <school_name>null</school_name>
                                        <groupCode>FG12</groupCode>
                                        <__code>MAS/KIM/0002</__code>
                                    </school_prompt>
                        </iics_survey_school_questionnaire_v1>"""
        def result = new XQL()
                .alterXML(sampleXml3)
                .add("groupCode")
                .withDefaultVal("FG12")
                .insertBefore("__code","school_prompt")

        assertTrue isSameXml(newXml,result)

    }

    @Test
    void testAlterXMLModifyTag(){
        def newXml = """<?xml version="1.0" encoding="UTF-8"?>
                        <iics_survey_school_questionnaire_v1>
                                <visitSchool>baseline</visitSchool>
                                    <school_prompt>
                                        <school_name>null</school_name>
                                        <__code>MAS/KIM/0002</__code>
                                    </school_prompt>
                        </iics_survey_school_questionnaire_v1>"""
        def result = new XQL()
                .alterXML(sampleXml3)
                .modify("visit")
                .toNewName("visitSchool")
                .executeModify()

        assertTrue isSameXml(newXml,result)

    }

    @Test
    void testUpdateXmlWithNodesHavingDomKeywords(){
        def newXml = """<?xml version="1.0" encoding="UTF-8"?><issd_project_farmer_registration_form_v1 formKey="issd_project_farmer_registration_form_v1" id="9" name="Farmer Registration form">
  <lsb_search>
    <lsb/>
    <name>Amatura CooperativeTest12</name>
    <lsb_code>WMO6039</lsb_code>
    <partner>CEFORD</partner>
    <district>buhweju</district>
    <subcounty>bihanga</subcounty>
  </lsb_search>
  <farmer_details>
    <farmer_name>AMBAYO JOACHIM</farmer_name>
    <sex>male</sex>
    <age>1964-01-01</age>
  </farmer_details>
  <zone_farmer>westnile</zone_farmer>
  <district_farmer>moyo</district_farmer>
  <subcountry_farmer>moyo</subcountry_farmer>
  <parish>Vurra</parish>
  <village>Vurra opi</village>
  <type_farmer>general_farmer</type_farmer>
  <date>2018-07-30 03:14:14 PM</date>
  <unique_id>uuid:6d4fa225-02da-4af7-a91e-85cc41494719</unique_id>
  <farmerid2/>
  <meta>
    <__start>2018-07-30T15:14:14.483EAT</__start>
    <__end>2018-07-30T15:14:14.483EAT</__end>
    <__today>2018-07-30</__today>
    <instanceID>uuid:aebe3d89-9bce-440d-a912-9afd3fdaf2be</instanceID>
    <__reviewed/>
    <__reviewedBy/>
  </meta>
</issd_project_farmer_registration_form_v1>
                """
        def result = new XQL()
                .update(sampleXml4.trim())
                .set('name')
                .to('Amatura CooperativeTest12')
                .where('name')
                .isEqualTo('Amatura Cooperative')
                .queryKeepNameSpaces()

        assertTrue isSameXml(newXml,result)
    }

    @Test
    void testUpdateXmlWithNameSpaceMissing(){
        def newXml = """<?xml version="1.0" encoding="UTF-8"?><issd_project_farmer_registration_form_v1 formKey="issd_project_farmer_registration_form_v1" id="9" name="Farmer Registration form">
  <lsb_search>
    <lsb/>
    <name>Amatura Cooperative</name>
    <lsb_code>WMO60399</lsb_code>
    <partner>CEFORD</partner>
    <district>buhweju</district>
    <subcounty>bihanga</subcounty>
  </lsb_search>
  <farmer_details>
    <farmer_name>AMBAYO JOACHIM</farmer_name>
    <sex>male</sex>
    <age>1964-01-01</age>
  </farmer_details>
  <zone_farmer>westnile</zone_farmer>
  <district_farmer>moyo</district_farmer>
  <subcountry_farmer>moyo</subcountry_farmer>
  <parish>Vurra</parish>
  <village>Vurra opi</village>
  <type_farmer>general_farmer</type_farmer>
  <date>2018-07-30 03:14:14 PM</date>
  <unique_id>uuid:6d4fa225-02da-4af7-a91e-85cc41494719</unique_id>
  <farmerid2/>
  <meta>
    <__start>2018-07-30T15:14:14.483EAT</__start>
    <__end>2018-07-30T15:14:14.483EAT</__end>
    <__today>2018-07-30</__today>
    <instanceID>uuid:aebe3d89-9bce-440d-a912-9afd3fdaf2be</instanceID>
    <__reviewed/>
    <__reviewedBy/>
  </meta>
</issd_project_farmer_registration_form_v1>
                """
        def result = new XQL()
                .update(sampleXml4.trim())
                .set('lsb_code')
                .to('WMO60399')
                .where('lsb_code')
                .isEqualTo('WMO6039')
                .queryKeepNameSpaces()

        assertTrue isSameXml(newXml,result)
    }

    @Test
    void testAlterXMLModifyTagForInnerElement(){
        def newXml = """<?xml version="1.0" encoding="UTF-8"?>
                        <iics_survey_school_questionnaire_v1>
                                <visit>baseline</visit>
                                    <school_prompt>
                                        <schoolName>null</schoolName>
                                        <__code>MAS/KIM/0002</__code>
                                    </school_prompt>
                        </iics_survey_school_questionnaire_v1>"""
        def result = new XQL()
                .alterXML(sampleXml3)
                .modify("school_name")
                .toNewName("schoolName")
                .executeModify()

        assertTrue isSameXml(newXml,result)

    }


}
