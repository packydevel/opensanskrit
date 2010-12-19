import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class ThreadedDialog 
{               
     
    public static void main(String[] args)
    {
        // Create a parent JFrame that contains a label
        JFrame parentFrame = new JFrame("JavaReference threaded dialog example");
        parentFrame.setSize(500, 150);
        JLabel jl = new JLabel();        
        jl.setText("Count : 0");
        
        // Perform layout to add label to the parent frame
        parentFrame.getContentPane().setLayout(new BorderLayout());
        parentFrame.getContentPane().add(BorderLayout.CENTER, jl);
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Display the parent frame
        parentFrame.setVisible(true);
        
        // Create a dialog that will display the progress.
        final JDialog dlg = new JDialog(parentFrame, "Progress Dialog", ModalityType.APPLICATION_MODAL);
        JProgressBar dpb = new JProgressBar(0, 500);
        dlg.getContentPane().setLayout(new BorderLayout());
        dlg.getContentPane().add(BorderLayout.CENTER, dpb);
        dlg.getContentPane().add(BorderLayout.NORTH, new JLabel("Progress..."));        
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dlg.setSize(300, 75);
        dlg.setLocationRelativeTo(parentFrame);                
        
        // Create a new thread and call show within the thread.
        Thread t = new Thread(new Runnable(){
                                    public void run()
                                    {
                                        dlg.show();
                                    }
                              });
                                      
        // Start the thread so that the dialog will show.
        t.start();
            
        // Perform computation. Since the modal dialogs show() was called in
        // a thread, the main thread is not blocked. We can continue with computation
        // in the main thread.
        for(int i =0; i<=500; i++)
        {            
            jl.setText("Count : " + i);
            // Set the value that the dialog will display on the progressbar.
            dpb.setValue(i);
            try
            {
                Thread.sleep(25);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }        
        }
        
        // Finished computation. Now hide the dialog. This will also stop the
        // thread since the "run" method will return.
        dlg.hide();
    }
} 