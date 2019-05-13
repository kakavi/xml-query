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

        assertTrue isSameXml(newxml,expected)
    }

    @Test
    void testUpdateFormDataWithRepeats() {
        def xql = new XQL('planting_returns')
                .update(xmlWithRepeats.trim().replaceAll("\n", ""))
                .set('district')
                .to('kakabouy')
                .where('__code')
                .isEqualTo('WPK6063/0003')
        def newxml = xql.queryKeepNameSpaces()

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

        assertTrue isSameXml(newxml,expected)
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

        assertTrue isSameXml(newxml,expected)
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

        assertTrue isSameXml(newxml,expected)
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


}
