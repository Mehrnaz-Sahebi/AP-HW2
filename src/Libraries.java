import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class Libraries {
    private HashSet<Library> librariesSet ;
    public Libraries (){
        librariesSet = new HashSet<Library>();
    }

    public void addLibrary(String libraryId, String name, int yearOfEstablishment, int countOfDesks, String address) {
        Library library = new Library(libraryId, name, yearOfEstablishment, countOfDesks, address);
        librariesSet.add(library);
    }


    public Library getLibrary(String libraryId){
        for (Library library:librariesSet) {
            if(library.getLibraryId().equals(libraryId)){
                return library;
            }
        }
        return null;
    }

    public void search(String searchKey){
        HashSet<String> searchResult = new HashSet<String>();
        String searchKeyToLowerCase = searchKey.toLowerCase();
        for (Library library:librariesSet) {
            for (Book book:library.getBooks()) {
                if(book.getBookId().toLowerCase().contains(searchKeyToLowerCase)||book.getPublisher().toLowerCase().contains(searchKeyToLowerCase)||book.getTitle().toLowerCase().contains(searchKeyToLowerCase))
                {
                    searchResult.add(book.getBookId());
                }
            }
            for (Thesis thesis:library.getTheses()) {
                if(thesis.getTitle().toLowerCase().contains(searchKeyToLowerCase)||thesis.getStudentName().toLowerCase().contains(searchKeyToLowerCase)||thesis.getSupervisor().contains(searchKeyToLowerCase))
                {
                    searchResult.add(thesis.getThesisId());
                }
            }
        }
        if(searchResult.size()==0){
            System.out.println("not-found");
            return;
        }
        ArrayList<String> sortedSearchResult = new ArrayList<String>();
        for (String element:searchResult) {
            sortedSearchResult.add(element);
        }
        sortedSearchResult.sort(String::compareToIgnoreCase);
        System.out.printf("%s",sortedSearchResult.get(0));
        for (int i = 1; i < sortedSearchResult.size(); i++) {
            System.out.printf("|%s",sortedSearchResult.get(i));
        }
        System.out.println();
    }
    public void categoryReport(String categoryId) {
        int countOfBooks = 0;
        int countOfTheses = 0;
        for (Library library : librariesSet) {
            for (Book book : library.getBooks()) {
                if (book.getCategory().equals(categoryId)) {
                    countOfBooks += book.getCountOfCopies();
                }
            }
            for (Thesis thesis : library.getTheses()) {
                if (thesis.getCategory().equals(categoryId)) {
                    countOfTheses++;
                }
            }
        }
        System.out.printf("%d %d\n", countOfBooks, countOfTheses);
    }
    public void libraryReport(String libraryId){
        int countOfBooks = 0;
        int countOfTheses = this.getLibrary(libraryId).getTheses().size();
        int countOfBorrowedBooks = 0;
        int countOfBorrowedTheses = 0;
        for (Book book:this.getLibrary(libraryId).getBooks()) {
            countOfBooks+=book.getCountOfCopies();
            countOfBorrowedBooks+=(book.getCountOfCopies()-book.getRemaining());
        }
        for (Thesis thesis:this.getLibrary(libraryId).getTheses()){
            if(thesis.isBorrowed()){
                countOfBorrowedTheses++;
            }
        }
        System.out.printf("%d %d %d %d\n",countOfBooks,countOfTheses,countOfBorrowedBooks,countOfBorrowedTheses);
    }
}
