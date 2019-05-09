package xmlquery

import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
                    </issd_project_planting_returns_form_v1>

                """

    @Before
    void setUp(){
//
    }

    @Test
    void testUpdate(){
        def xql = new XQL('lsb_search')
                .update(stringXML)
                .set('lsb')
                .to('Kakavii')
                .where('__code')
                .isEqualTo('NLA60105')

        def newxml = xql.query()

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

        assertTrue newxml.equals(expected)
    }

    @Test
    void testUpdateWithDepthFirst(){
        def xql = new XQL()
                .update(stringXML)
                .set('lsb')
                .to('Kakavii')
                .where('__code')
                .isEqualTo('NLA60105')

        def newxml = xql.query()

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

        assertTrue newxml.equals(expected)
    }

    @Test
    void testUpdateFormDataWithRepeats(){
        def xql = new XQL('planting_returns')
                .update(xmlWithRepeats.trim())
                .set('district')
                .to('kakabouy')
                .where('__code')
                .isEqualTo('WPK6063/0003')
        def newxml = xql.query()

        def expected = """<issd_project_planting_returns_form_v1 id="13" name="Planting Returns Form">
  <instruction/>
  <season>
    _2019a
  </season>
  <crop>
    cassava
  </crop>
  <variety_cassava>
    narocase_1
  </variety_cassava>
  <seed_class>
    foundation_seed
  </seed_class>
  <planting_returns>
    <farmer/>
    <farmer_name>
      Owiny Bruno
    </farmer_name>
    <__code>
      WPK6063/0003
    </__code>
    <lsb_name>
      Pokwero
    </lsb_name>
    <partner>
      WENIPS
    </partner>
    <district>
      kakabouy
    </district>
    <subcounty>
      panyango
    </subcounty>
    <zone>
      westnile
    </zone>
    <arceage>
      1.0
    </arceage>
    <quantity_planted_bags>
      6.0
    </quantity_planted_bags>
    <planting_date>
      2019-04-06
    </planting_date>
  </planting_returns>
  <planting_returns>
    <farmer/>
    <farmer_name>
      Alli Quirino
    </farmer_name>
    <__code>
      WPK6063/0006
    </__code>
    <lsb_name>
      Pokwero
    </lsb_name>
    <partner>
      WENIPS
    </partner>
    <district>
      pakwach
    </district>
    <subcounty>
      panyango
    </subcounty>
    <zone>
      westnile
    </zone>
    <arceage>
      1.0
    </arceage>
    <quantity_planted_bags>
      6.0
    </quantity_planted_bags>
    <planting_date>
      2019-04-09
    </planting_date>
  </planting_returns>
  <planting_returns>
    <farmer/>
    <farmer_name>
      BLOCK_Pokwero
    </farmer_name>
    <__code>
      WPK6063/0046
    </__code>
    <lsb_name>
      Pokwero
    </lsb_name>
    <partner>
      WENIPS
    </partner>
    <district>
      pakwach
    </district>
    <subcounty>
      panyango
    </subcounty>
    <zone>
      westnile
    </zone>
    <arceage>
      8.0
    </arceage>
    <quantity_planted_bags>
      48.0
    </quantity_planted_bags>
    <planting_date>
      2019-04-03
    </planting_date>
  </planting_returns>
  <date>
    2019-04-30 01:24:51 PM
  </date>
  <unique_id>
    uuid:30263fff-af7e-4443-a048-d1cc7a178afe
  </unique_id>
</issd_project_planting_returns_form_v1>
"""

        assertTrue newxml.equals(expected)
    }

    @Test
    void testUpdateFormDataWithRepeatsUsingDepthFirst(){

        def xql = new XQL()
                .update(xmlWithRepeats.trim())
                .set('district')
                .to('kakabouy')
                .where('__code')
                .isEqualTo('WPK6063/0003')
        def newxml = xql.query()

        def expected = """<issd_project_planting_returns_form_v1 id="13" name="Planting Returns Form">
  <instruction/>
  <season>
    _2019a
  </season>
  <crop>
    cassava
  </crop>
  <variety_cassava>
    narocase_1
  </variety_cassava>
  <seed_class>
    foundation_seed
  </seed_class>
  <planting_returns>
    <farmer/>
    <farmer_name>
      Owiny Bruno
    </farmer_name>
    <__code>
      WPK6063/0003
    </__code>
    <lsb_name>
      Pokwero
    </lsb_name>
    <partner>
      WENIPS
    </partner>
    <district>
      kakabouy
    </district>
    <subcounty>
      panyango
    </subcounty>
    <zone>
      westnile
    </zone>
    <arceage>
      1.0
    </arceage>
    <quantity_planted_bags>
      6.0
    </quantity_planted_bags>
    <planting_date>
      2019-04-06
    </planting_date>
  </planting_returns>
  <planting_returns>
    <farmer/>
    <farmer_name>
      Alli Quirino
    </farmer_name>
    <__code>
      WPK6063/0006
    </__code>
    <lsb_name>
      Pokwero
    </lsb_name>
    <partner>
      WENIPS
    </partner>
    <district>
      pakwach
    </district>
    <subcounty>
      panyango
    </subcounty>
    <zone>
      westnile
    </zone>
    <arceage>
      1.0
    </arceage>
    <quantity_planted_bags>
      6.0
    </quantity_planted_bags>
    <planting_date>
      2019-04-09
    </planting_date>
  </planting_returns>
  <planting_returns>
    <farmer/>
    <farmer_name>
      BLOCK_Pokwero
    </farmer_name>
    <__code>
      WPK6063/0046
    </__code>
    <lsb_name>
      Pokwero
    </lsb_name>
    <partner>
      WENIPS
    </partner>
    <district>
      pakwach
    </district>
    <subcounty>
      panyango
    </subcounty>
    <zone>
      westnile
    </zone>
    <arceage>
      8.0
    </arceage>
    <quantity_planted_bags>
      48.0
    </quantity_planted_bags>
    <planting_date>
      2019-04-03
    </planting_date>
  </planting_returns>
  <date>
    2019-04-30 01:24:51 PM
  </date>
  <unique_id>
    uuid:30263fff-af7e-4443-a048-d1cc7a178afe
  </unique_id>
</issd_project_planting_returns_form_v1>
"""

        assertTrue newxml.equals(expected)
    }
}
