package kansallispuistot;

/**
 * @author vilikelo
 * @version 25 Oct 2022
 *
 */
public class SailoException extends Exception {

    private static final long serialVersionUID = 1L;

    
    /**
     * @param viesti on viesti mikä viedään 
     */
    public SailoException(String viesti) {
        super(viesti);
    }


}
