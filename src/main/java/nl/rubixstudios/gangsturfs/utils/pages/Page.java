package nl.rubixstudios.gangsturfs.utils.pages;

public abstract class Page {

    private final int listSize;
    private final int currentPage;

    private int totalPages;
    private int linesPerPage;

    /**
     * This constructor is used to make the Page object.
     * @param linesPerPage The amount of lines you want for each page.
     * @param listSize The size of the list you want to split in pages.
     * @param currentPage The currentPage you have to be on.
     */
    public Page(int linesPerPage, int listSize, int currentPage) {
        this.linesPerPage = linesPerPage;
        this.listSize = listSize;
        this.currentPage = currentPage;

        this.execute();
    }

    private void execute() {
        if (listSize < linesPerPage) {
            linesPerPage = listSize;
        }

        totalPages = listSize / linesPerPage;
        if ((listSize % linesPerPage) > 0) {
            totalPages++;
        }

        if (currentPage > totalPages) throw new PageException("currentPage can't be bigger than totalPages");

        this.runBefore();

        final int begin = (currentPage -1) * linesPerPage;
        final int end = currentPage * linesPerPage;

        for (int i = begin; i < end; i++) {
            try {
                this.run(i);
            } catch (IndexOutOfBoundsException e) {
                break;
            }

        }

        this.runAfter();
    }

    /**
     * This method is used to run before.
     */
    public abstract void runBefore();

    /**
     * This method is used to run, for each page line.
     * @param index The index of the list its on.
     */
    public abstract void run(int index);

    /**
     * This method is used to run after.
     */
    public abstract void runAfter();

    /**
     * This method is used to get the lines per page.
     * @return int
     */
    public int getLinesPerPage() {
        return linesPerPage;
    }

    /**
     * This method is used to get the list size.
     * @return int
     */
    public int getListSize() {
        return listSize;
    }

    /**
     * This method is used to get the current page you're on.
     * @return int
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * This method is used to get to total amount of pages.
     * @return
     */
    public int getTotalPages() {
        return totalPages;
    }
}
