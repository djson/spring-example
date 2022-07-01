@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SampleController {

    /**
     * Annotation to cors
     */
    @RequestMapping("/getData", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public int SampleGetData() {
    	/* ... */
        return 200;
    }
}