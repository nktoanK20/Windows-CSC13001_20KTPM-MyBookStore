package BUS;

import java.util.ArrayList;

import DAO.PublisherDAO;
import POJO.PublisherPOJO;


public class PublisherBUS {
	public ArrayList<PublisherPOJO> getAllPublisher(){
		ArrayList<PublisherPOJO> result = null;
		PublisherDAO publisherDA = new PublisherDAO();
		result = publisherDA.getAllPublisher();
		return result;
	}

  public ArrayList<PublisherPOJO> getPublisherNotDisable(){
		ArrayList<PublisherPOJO> result = null;
		PublisherDAO publisherDA = new PublisherDAO();
		result = publisherDA.getPublisherNotDisable();
		return result;
	}

  public boolean addPublisher(PublisherPOJO publisher){
    PublisherDAO publisherDA = new PublisherDAO();
    boolean res = publisherDA.addPublisher(publisher);
    return res;
  }
  public boolean updatePublisher(PublisherPOJO publisher){
    PublisherDAO publisherDA = new PublisherDAO();
    boolean res = publisherDA.updatePublisher(publisher);
    return res;
  }

  public ArrayList<PublisherPOJO> getPublisherBySearch(String id, String name){
    PublisherDAO publisherDA = new PublisherDAO();
    ArrayList<PublisherPOJO> listAuthor = new ArrayList<>();
    listAuthor = publisherDA.getPublisherBySearch(id,name);
    return listAuthor;
  }

  public boolean enablePublisher(PublisherPOJO publisher){
    PublisherDAO publisherDA  = new PublisherDAO();
    boolean res = publisherDA.enablePublisher(publisher);
    return res;
  }

  public boolean disablePublisher(PublisherPOJO publisher){
    PublisherDAO publisherDA  = new PublisherDAO();
    boolean res = publisherDA.disablePublisher(publisher);
    return res;
  }
  public static ArrayList<PublisherPOJO> getAll(){
        return PublisherDAO.getAll();
    }

    public static ArrayList<String> getAllId(){
        ArrayList<String> result = new ArrayList<>();
        ArrayList<PublisherPOJO> publishers = getAll();
        for (PublisherPOJO publisher : publishers) {
            result.add(publisher.getId());
        }

        return result;
    }
}
