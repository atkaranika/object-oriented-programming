/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.BorderLayout;
import java.awt.Button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import java.io.File ;
import static java.nio.file.AccessMode.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
/**
 *
 * @author 99kar
 */
public class FileBrowser {

    public static final int WIDTH = 700;
    public static final int HEIGHT = 600;
    public  static Container mainpanel;
    private static JFrame window = new JFrame();
    private static String currentDirectory;
    private static  String []AllFilesArray ;
    private static  List AllFilesList ;
    private static  List HiddenFilesList ;
    //panels
    private static JPanel c ;
    private static JPanel upPanel ;
    private static JPanel favorites ;
    private static JPanel pathPanel ; //tha mpei sto c 
    private static JPanel contentPanel ; //tha mpei sto c 
    private static JPanel searchPanel ; //tha mpei sto c 
    private static JPanel searchResultPanel ; //tha mpei sto c einaigia to search
    //menu bar
    private static JMenuBar bar ;
            
    //Jmenus 
    private static JMenu File;
    private static JMenu Edit;
    private static JMenu View;
    //Jmenuitem
    private static JMenuItem NewWindow;
    private static JMenuItem Exit;
    private static JMenuItem Cut;
    private static JMenuItem Copy;
    private static JMenuItem Paste;
    private static JMenuItem Rename;
    private static JMenuItem Delete;
    private static JMenuItem AddtoFavourites;
    private static JMenuItem Properties;
    private static JCheckBoxMenuItem  Search;
    private static JCheckBoxMenuItem  HiddenFiles;
    
    private static JTextField searchText;
    
    private static JButton searchButton;
    
    private static JList<JLabel> list;
    private static DefaultListModel<JLabel> listModel;
    private static JScrollPane listScrollPane ;
    private static JScrollPane contentScroll;
    
    private static  JMenuItem menuItem;
    
    private static int existCopy = 0 ; //ginete 1 an kapoio arxeio antigrafei
    private static String pathSelected ;
    private static String fromDirectory;    //otan kanoume copy pairnoume to trexwn katalogo gia na kanoume meta disable to paste 
    private static String toDirectory ;     
    private static String fileToCopy ;      //otan kanoume copy arxikopoieitai
    
      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        filebrowser();
      
    }
    public static void filebrowser(){
        
        window.setResizable(true);
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addPanel();
        AddMenu();
        window.setVisible(true);
        
        
    }
    //add menu 
    public static void  AddMenu(){
        File = new JMenu("File"); 
        NewWindow = new JMenuItem("New Window");
        //listener
        File.add(NewWindow);
        
        Exit = new JMenuItem("Exit");
        //listener
        File.add(Exit);
        
        Edit = new JMenu("Edit"); 
        Cut = new JMenuItem("Cut");
        //listener
        Edit.add(Cut);
        Copy = new JMenuItem("Copy");
        //listener
        Edit.add(Copy);
        Paste = new JMenuItem("Paste");
        //listener
        Edit.add(Paste);
        Rename = new JMenuItem("Rename");
        //listener
        Edit.add(Rename);
        
        Delete = new JMenuItem("Delete");
        //listener
        Edit.add(Delete);
        AddtoFavourites = new JMenuItem("Add to Favourites");
        //listener
        Edit.add(AddtoFavourites);
        
        Properties = new JMenuItem("Properties");
        //listener
        Edit.add(Properties);
        
        View = new JMenu("View"); 
        Search = new JCheckBoxMenuItem("Search");
        Search.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
             //tha doume an to search epilexthike h apo epilexthike
             if (e.getStateChange() == ItemEvent.DESELECTED) {
                 searchPanel.setVisible(false);
             }
             else searchPanel.setVisible(true);
           
            }
         });
        //listener
        View.add(Search);
        
        HiddenFiles = new JCheckBoxMenuItem("Hidden Files/Folders");
        //listener
        View.add(HiddenFiles);
        
        
        //Menu Bar
       
        bar = new JMenuBar();
        bar.add(File);
        bar.add(Edit);
        bar.add(View);
       
        Border barline = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        bar.setBorder(barline);

        window.setJMenuBar(bar);
   
        
    }
    
    public static  void addPanel(){
        mainpanel = window.getContentPane();
        mainpanel.setLayout(new BorderLayout());
        searchPanel = new JPanel();
        c = new JPanel();
        c.setLayout(new BorderLayout());
        c.add(searchPanel,BorderLayout.NORTH);
        
        upPanel = new JPanel();
        upPanel.setLayout(new BorderLayout());
        upPanel.setBackground(Color.PINK);
 
        upPanel.setLayout(new BorderLayout());
        upPanel.setVisible(true);
        upPanel.setBackground(Color.CYAN);
        
        mainpanel.add(upPanel,BorderLayout.NORTH); 
        JLabel favorites_files = new JLabel("Favorites Files");
        favorites = new JPanel();
        favorites.setLayout(new BorderLayout());
        favorites.add(favorites_files);
        
        mainpanel.add(favorites,BorderLayout.WEST); 
        favorites.setVisible(true);
        
        mainpanel.setVisible(true);
        //ksekinima apo home directory
        currentDirectory = System.getProperty("user.home");
        
        content_directory();
        content_search();
    }
    
    public static void content_directory(){
      
        c.removeAll();
        c.setLayout(new BorderLayout());    
        contentPanel = new JPanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 10,10));
        contentScroll = new JScrollPane(contentPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        c.setBackground(Color.BLUE);
        
        mainpanel.add(c,BorderLayout.CENTER);
        c.add(contentScroll, BorderLayout.CENTER);
        
        File directory = new File(currentDirectory);
        AllFilesArray = directory.list();         //pairnoume ola ta arxika kai tous katalogous ston home directory 
        
        /*
        System.out.println("-------------  " + currentDirectory + " ------------------ ");
        for (int i = 0 ; i < AllFilesArray.length ; i++){
            
            System.out.println(AllFilesArray[i]);
        }
        File[] AllFiles = directory.listFiles();
        System.out.println("                    -------------  " );
        for (int i = 0 ; i < AllFiles.length ; i++){
            
            System.out.println(AllFiles[i].getName());
        }
*/
        pathPanel = new JPanel();
        JPanel inPanel = new JPanel();
        inPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pathPanel.setLayout(new BorderLayout());
        //kathe fora pou allazoume katalogo vazoume kai to search panel 
        pathPanel.add(searchPanel,BorderLayout.NORTH);
        
        //ftixnoume to breadcrumb
        Path path = Paths.get(currentDirectory);
        JLabel name;
        JPopupMenu popup = new JPopupMenu();
        menuItem = new JMenuItem("Cut");

        popup.add(menuItem);
        menuItem = new JMenuItem("Copy");
        menuItem.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                int index = pathSelected.toString().lastIndexOf(System.getProperty("file.separator") );
                fileToCopy = new String(pathSelected.toString());
                fromDirectory =  new String(pathSelected.toString().substring(0,index));
                existCopy = 1 ;
                System.out.println();
            }
        });
        
        popup.add(menuItem);
        menuItem = new JMenuItem("paste");
        menuItem.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                System.out.println("paste " + " from " + fromDirectory +" to  "+ toDirectory + " fileToCopy " + fileToCopy + " pathselected " + pathSelected);
                existCopy = 0 ;
                copyFile(fileToCopy, toDirectory);
            
            }
        });
        
        menuItem.setEnabled(false);
        popup.add(menuItem);
        menuItem = new JMenuItem("Rename");
        popup.add(menuItem);
        menuItem = new JMenuItem("Delete");
        popup.add(menuItem);
        menuItem = new JMenuItem("Add to Favourites");
        popup.add(menuItem);
        menuItem = new JMenuItem("Properties");
        popup.add(menuItem);
                
       
        for(int i = 0 ; i <=  path.getNameCount() ; i++){
            if (i == 0){
                name = new JLabel(path.getRoot().toString().substring(0,path.getRoot().toString().length() - 1));
                name.setName(path.getRoot().toString().substring(0,path.getRoot().toString().length() - 1));
                name.addMouseListener(new MouseAdapter() {

                     @Override
                     public void mouseClicked(MouseEvent e) {
                        if(SwingUtilities.isLeftMouseButton(e)){
                            
                            Path root = path.getRoot();
                            currentDirectory = root.toString();
                            System.out.println("------>"+currentDirectory);
                            content_directory();
                              
                        }
                    }
                    }); 
            }
            else {
                name = new JLabel(path.getName(i-1).toString());
                name.setName(path.getName(i-1).toString());
                name.addMouseListener(new MouseAdapter() {
                    
                     @Override
                     public void mouseClicked(MouseEvent e) {
                        if(SwingUtilities.isLeftMouseButton(e)){
                            
                             for(int j = 0 ; j <=  path.getNameCount(); j++){
                                
                                if(path.getName(j).toString().equals(((JLabel) e.getSource()).getName())){
                                   
                                    Path newPath = path.subpath(0,j+1);
                                    Path root = path.getRoot();
                                    newPath = root.resolve(newPath);
                                            
                                    currentDirectory = newPath.toString();
                                    System.out.println(currentDirectory);
                                    
                                    content_directory();
                                    break ;
                                }
                            }
                        }
                    }
                    }); 
            }
            
            inPanel.add(name);
   
            if (i < path.getNameCount()){
                JLabel delimiter = new JLabel(" > ");
                inPanel.add(delimiter);
            }

        }
        pathPanel.add(inPanel,BorderLayout.WEST);
        c.add(pathPanel,BorderLayout.NORTH);
        if(AllFilesArray == null){
            return ;
        }
        List <String>sortedList = new LinkedList(sortList(AllFilesArray));
        Iterator <String>iter = sortedList.iterator();
        String z ;
        while(iter.hasNext()){
            z = iter.next();

            Path filePath = Paths.get(currentDirectory.toString(), z);
         
            File file = new File(filePath.toString());
            
            if(file.isDirectory()){
                ImageIcon icon = new ImageIcon(new ImageIcon("./icons/folder.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                String  text = file.getName();
                
                JLabel nextDirectory = new JLabel(text,icon,JLabel.CENTER);
                nextDirectory.setPreferredSize(new Dimension(60, 70));
                nextDirectory.setToolTipText(file.getName());
                
                nextDirectory.setHorizontalTextPosition(JLabel.CENTER);
                nextDirectory.setVerticalTextPosition(JLabel.BOTTOM); 
                nextDirectory.setHorizontalTextPosition(JLabel.CENTER);
                nextDirectory.setVerticalTextPosition(JLabel.BOTTOM);
                

                nextDirectory.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                   if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2){
                       String newDirectory = filePath.toString();
                       System.out.println("CHANGE"+ currentDirectory);
                       if(file.canWrite()){
                            File directory = new File(newDirectory);
                            if(directory.list() != null){
                                currentDirectory = newDirectory ;
                            }
                           
                           content_directory();
                       }
                       //ta dikaiwmata
                  }
                   if(SwingUtilities.isRightMouseButton(e)){
                    pathSelected = filePath.toString() ;
                    System.out.println("Path  "+pathSelected);
                    toDirectory = pathSelected.toString();
                    
                    System.out.println("path "+ pathSelected+ " to " + toDirectory);
                    
                    
                      if(existCopy == 1 && !fromDirectory.equals(toDirectory)){
                          popup.getComponent(2).setEnabled(true);
                      }
                      else {
                          popup.getComponent(2).setEnabled(false);
                      }
                     
                      
                      popup.show(e.getComponent(),e.getX(), e.getY());
                  
                  }
               }
               });
                //ama den mporo na diabaso to arxeio to kanw unclickable 
            
               try
               {
                   
                   filePath.getFileSystem().provider().checkAccess(filePath,READ);
      
               }
               catch(IOException e)
               {    
                   nextDirectory.setEnabled(false);
                   System.out.println ("File cannot be used for this application");
               }
        
                
                contentPanel.add(nextDirectory);
            }

        }
        
        iter = sortedList.iterator();
      
        while(iter.hasNext()){
            z = iter.next();

            Path filePath = Paths.get(currentDirectory.toString(), z);
            
            File file = new File(filePath.toString());
           // System.out.println( currentDirectory );
            if(file.isFile()){
                
                String  text = file.getName();
                String fileTyle =text.substring(text.lastIndexOf(".")+1,text.length());
               // System.out.println(fileTyle);
                ImageIcon icon = new ImageIcon(new ImageIcon("./icons/question.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                
                if((new File(("./icons/"+fileTyle+".png"))).exists()){
                    
                   icon  = new ImageIcon(new ImageIcon("./icons/"+fileTyle+".png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                }
               
                
                
                JLabel nextDirectory = new JLabel(text,icon,JLabel.CENTER);
                nextDirectory.setPreferredSize(new Dimension(60, 70));
                nextDirectory.setToolTipText(file.getName());
                
                nextDirectory.setHorizontalTextPosition(JLabel.CENTER);
                nextDirectory.setVerticalTextPosition(JLabel.BOTTOM); 
                nextDirectory.setHorizontalTextPosition(JLabel.CENTER);
                nextDirectory.setVerticalTextPosition(JLabel.BOTTOM);
                nextDirectory.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                   if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2){
                       try {
                           Desktop.getDesktop().open(file);
                           
                       } catch (IOException ex) {
                           Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                       }
                  }
                  if(SwingUtilities.isRightMouseButton(e)){
                    pathSelected = filePath.toString() ;
                    System.out.println("Path  "+pathSelected);

                    popup.getComponent(2).setEnabled(false);

                    popup.show(e.getComponent(),
                          e.getX(), e.getY());
                  }
                  
               }
               });
                 
               
               
                contentPanel.add(nextDirectory);
            }

        }
        
     
        contentPanel.updateUI();
        c.updateUI();
        
        
    }
    
    
    //auth h sunartisi orizi to periexomeno tou searchPanel
    public static void content_search(){
        
        searchText = new JTextField();
        searchResultPanel = new JPanel(new BorderLayout());
        searchPanel.setVisible(false);
        searchPanel.setLayout(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();
        con.weightx=1.0;
        con.fill=GridBagConstraints.HORIZONTAL;
        searchPanel.add(searchText,con); 
        searchPanel.add(new JPanel(){
            {    
                searchButton = new JButton("Search");
                searchButton.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                        String serchText = searchText.getText();
                       
                            System.out.println(currentDirectory);
                            listModel = new DefaultListModel<JLabel>();
                            search(currentDirectory,serchText );
                            list = new JList(listModel);
                            list.setCellRenderer(new LabelListCellRenderer());
                            listScrollPane = new JScrollPane(list);
                            searchResultPanel.add(listScrollPane);
                            
                            c.add(searchResultPanel,BorderLayout.CENTER);
                            c.remove(contentScroll);
                            c.revalidate();
                            
                            
                        
                     }
                });
               add(searchButton); 
            }
        }); 
        
                            
        
       // pathPanel.add(searchPanel,BorderLayout.NORTH);
    }
    
    public static List search(String directory, String searchText ){
        List <String>retList = new LinkedList();
        File dir = new File(directory);
        
        Path inputPath = Paths.get(directory);
        Path fullPath = inputPath.toAbsolutePath();
        //System.out.println("mydirecotry "+directory);
       // System.out.println(inputPath +" inputPath "+fullPath +" fullPath " );
        String allFiles[] = dir.list();
        
        if(allFiles == null){
            return retList;
        }
        String part = new String();
        String suffix = new String() ;
        if(searchText.contains(" ")){
            part = searchText.substring(0, searchText.indexOf(" "));
            suffix = searchText.substring( searchText.indexOf(" ")+1, searchText.length());
           // System.out.println("part " + part + " suffix "+ suffix);
            //System.out.println(currentDirectory);
        }
        for (int i = 0 ; i < allFiles.length ; i++){
            System.out.println("directory "+ directory + " al files " + allFiles[i]);
        }
        for (int i = 0 ; i < allFiles.length ; i++){
         
               
            String path = directory +  System.getProperty("file.separator") + allFiles[i];
           // System.out.println(path );
            File current = new File(path);
            
             if(current.isDirectory()){
                 System.out.println("current " + directory + " from "+ path );
                 
                 retList.addAll(search(path, searchText));
                 System.out.println("List"+retList );
                 
             }
             else {
                 if(searchText.contains(" ")){
                     if(part.toLowerCase().equals(dir)){
                         if(allFiles[i].toLowerCase().endsWith(suffix.toLowerCase())){
                            ImageIcon icon = new ImageIcon(new ImageIcon("./icons/dir.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                            JLabel next_result = new JLabel(path,icon, JLabel.CENTER);
                            listModel.addElement(next_result);
                            retList.add(path);
                         }
                     }
                    else if(allFiles[i].toLowerCase().endsWith(suffix.toLowerCase()) && allFiles[i].toLowerCase().contains(part.toLowerCase())){
                        int dotIndex = path.lastIndexOf('.');
                        String fileTyle =  path.substring(dotIndex + 1);
                        ImageIcon icon = new ImageIcon(new ImageIcon("./icons/question.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

                        if((new File(("./icons/"+fileTyle+".png"))).exists()){

                           icon  = new ImageIcon(new ImageIcon("./icons/"+fileTyle+".png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                        }
                        JLabel next_result = new JLabel(path,icon, JLabel.CENTER);
                        listModel.addElement(next_result);
                        retList.add(path);
                    }
                 }
                 else if(allFiles[i].toLowerCase().contains(searchText.toLowerCase())){
                    
                    int dotIndex = path.lastIndexOf('.');
                   
                    String fileTyle =  path.substring(dotIndex + 1);
                    ImageIcon icon = new ImageIcon(new ImageIcon("./icons/question.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                
                    if((new File(("./icons/"+fileTyle+".png"))).exists()){

                       icon  = new ImageIcon(new ImageIcon("./icons/"+fileTyle+".png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                    }
                
                
                    
                    JLabel next_result = new JLabel(path, icon,JLabel.CENTER );
                   
                     next_result.setHorizontalTextPosition(SwingConstants.RIGHT);
                     next_result.setVerticalTextPosition(JLabel.CENTER);
                     listModel.addElement(next_result);  
                     retList.add(path);
                    }
             }
         
        }
                
        
        return retList ;
    }
    
    public static List sortList(String[] files){
        AllFilesList = new LinkedList();
        HiddenFilesList = new LinkedList();
        for(int i = 0; i < files.length ; i++){
            if(files[i].charAt(0) == '.'){
                AllFilesList.add(files[i].substring(1, files[i].length()));
                HiddenFilesList.add(files[i]);
            }
            else {
                 AllFilesList.add(files[i]);
            }
        }
        Collections.sort(AllFilesList);
        return AllFilesList;
    }
    
    
    static class LabelListCellRenderer extends JLabel implements ListCellRenderer<JLabel> {
        private JLabel label;

        
       @Override
        public Component getListCellRendererComponent(
            JList list,
            JLabel value,
            int index,
            boolean selected,
            boolean expanded) 
        {
            this.setOpaque(true);
            setText(value.getText());
            setIcon(value.getIcon());
        return this;
    }

    }
    
    class PopupListener extends MouseAdapter {
        JPopupMenu popup;

        PopupListener(JPopupMenu popupMenu) {
          popup = popupMenu;
        }

        public void mousePressed(MouseEvent e) {
          maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
          maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
          if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                   e.getX(), e.getY());
          }
        }
      }
    
    private static void copyFile(String source, String  dest) {
        File sourceFile = new File(source); 
         
                
        
        
                
        if (sourceFile.isDirectory()){
            int index = source.lastIndexOf(System.getProperty("file.separator") );
            String destinationString = dest + source.substring(index, source.length());
            File destinationFolder  = new File(destinationString);
            if(destinationFolder.exists()){
                 String small_pieces = JOptionPane.showInputDialog(null,"Give pieces for small",null);
            }
            destinationFolder.mkdir();
           
            System.out.println( sourceFile + "   "+ sourceFile.list());
            File [] allFiles = sourceFile.listFiles();
             for(int i = 0 ; i < allFiles.length; i++){
                 System.out.println( sourceFile + "   "+ allFiles[i]);
             }
            for(int i = 0 ; i < allFiles.length; i++){
                if(allFiles[i].isFile()){
                    FileChannel sourceChannel = null;
                    FileChannel destChannel = null;
                   try {
                       
                        File destinationFile  = new File(destinationString + System.getProperty("file.separator")+ allFiles[i].getName());
                        destinationFile.createNewFile();
                        System.out.println("dhmiourgia arxeiou "+ destinationString + System.getProperty("file.separator")+ allFiles[i].getName());
                        /*
                        sourceChannel = new FileInputStream(allFiles[i]).getChannel();
                        destChannel = new FileOutputStream(destinationFile).getChannel();
                        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
*/
                       } catch (FileNotFoundException ex) {
                        Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                    }finally{/*
                        try {
                            
                            sourceChannel.close();
                            destChannel.close();/
                        } catch (IOException ex) {
                            Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }*/

                    }
                }
                else {
                    System.out.println("anadromi me source  "+ source + System.getProperty("file.separator") + allFiles[i].getName()+ " destination " + destinationString);
                    String newSource = source + System.getProperty("file.separator") + allFiles[i].getName();
                    copyFile(newSource, destinationString);
                }
               
            }
            return ; 
            
        }
        
        
    }
}
