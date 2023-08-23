package api.mongo.nosql.Domain.exception;

public class CollectionException extends Exception{

    private static final long serialVersionUID = 1L;

    public CollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Document with " + id + " not found!";
    }
    public static String RelationshipNotFoundException(String id1,String id2) {
        String ids = id1 + " , " + id2;
        return "Document with " + ids + " not found!";
    }

}
