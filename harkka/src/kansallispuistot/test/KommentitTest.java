package kansallispuistot.test;
// Generated by ComTest BEGIN
import java.io.File;
import kansallispuistot.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.12.07 18:27:15 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KommentitTest {


  // Generated by ComTest BEGIN
  /** testIterator176 */
  @Test
  public void testIterator176() {    // Kommentit: 176
    Kommentit harrasteet = new Kommentit(); 
    Kommentti pitsi21 = new Kommentti(2); harrasteet.lisaa(pitsi21); 
    Kommentti pitsi11 = new Kommentti(1); harrasteet.lisaa(pitsi11); 
    Kommentti pitsi22 = new Kommentti(2); harrasteet.lisaa(pitsi22); 
    Kommentti pitsi12 = new Kommentti(1); harrasteet.lisaa(pitsi12); 
    Kommentti pitsi23 = new Kommentti(2); harrasteet.lisaa(pitsi23); 
    Iterator<Kommentti> i2=harrasteet.iterator(); 
    assertEquals("From: Kommentit line: 188", pitsi21, i2.next()); 
    assertEquals("From: Kommentit line: 189", pitsi11, i2.next()); 
    assertEquals("From: Kommentit line: 190", pitsi22, i2.next()); 
    assertEquals("From: Kommentit line: 191", pitsi12, i2.next()); 
    assertEquals("From: Kommentit line: 192", pitsi23, i2.next()); 
    try {
    assertEquals("From: Kommentit line: 193", pitsi12, i2.next()); 
    fail("Kommentit: 193 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
    int n = 0; 
    int jnrot[] = { 2,1,2,1,2} ; 
    for ( Kommentti har:harrasteet ) {
    assertEquals("From: Kommentit line: 199", jnrot[n], har.getJasenNro()); n++; 
    }
    assertEquals("From: Kommentit line: 202", 5, n); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaKommentit216 */
  @Test
  public void testAnnaKommentit216() {    // Kommentit: 216
    Kommentit harrasteet = new Kommentit(); 
    Kommentti pitsi21 = new Kommentti(2); harrasteet.lisaa(pitsi21); 
    Kommentti pitsi11 = new Kommentti(1); harrasteet.lisaa(pitsi11); 
    Kommentti pitsi22 = new Kommentti(2); harrasteet.lisaa(pitsi22); 
    Kommentti pitsi12 = new Kommentti(1); harrasteet.lisaa(pitsi12); 
    Kommentti pitsi23 = new Kommentti(2); harrasteet.lisaa(pitsi23); 
    Kommentti pitsi51 = new Kommentti(5); harrasteet.lisaa(pitsi51); 
    List<Kommentti> loytyneet; 
    loytyneet = harrasteet.annaKommentit(3); 
    assertEquals("From: Kommentit line: 229", 0, loytyneet.size()); 
    loytyneet = harrasteet.annaKommentit(1); 
    assertEquals("From: Kommentit line: 231", 2, loytyneet.size()); 
    assertEquals("From: Kommentit line: 232", true, loytyneet.get(0) == pitsi11); 
    assertEquals("From: Kommentit line: 233", true, loytyneet.get(1) == pitsi12); 
    loytyneet = harrasteet.annaKommentit(5); 
    assertEquals("From: Kommentit line: 235", 1, loytyneet.size()); 
    assertEquals("From: Kommentit line: 236", true, loytyneet.get(0) == pitsi51); 
  } // Generated by ComTest END
}