package com.tolstoy.testjc;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;
import com.jconfig.*;

public class BrowserTest {
	public BrowserTest() {
		BrowserTestFrame		aFrame;

		aFrame = new BrowserTestFrame( "Disk Browser Test" );
//		aFrame.reshape( 100, 100, 500, 400 );
		aFrame.show();
	}

	public static void main( String args[] ) {
		FileSystem			systems[];
		File				curDir;
		int					i;

		try {
		System.err.println( "ABOUT TO LOAD NATIVE" );
			//Runtime.getRuntime().loadLibrary( "JConfigOSX" );
			Runtime.getRuntime().loadLibrary( "Example" );
		System.err.println( "LOADED NATIVE" );

			BrowserTest browser = new BrowserTest();
		}
		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
	}
}

class BrowserTestFrame extends Frame {
	int						currentLevel;
	BrowserTestPanel		thePanels[];
	Button					upALevelButton;

	static final int		kNumPanels = 5;

	public BrowserTestFrame( String ttl ) {
		super( ttl );
		currentLevel = 0;
		initializeDisplay();
	}

	public void refreshCurrentDisplay() {
		int					i;

		for ( i = 0; i < kNumPanels; i++ ) {
			thePanels[ i ].setLevel( currentLevel );
			thePanels[ i ].repaint();
		}
	}

	void initializeDisplay() {
		int				i;

		setLayout( new GridLayout( 1, 6 ) );
		resize( 500, 400 );
//		reshape( 100, 100, 500, 400 );

		thePanels = new BrowserTestPanel[ kNumPanels ];		
		for ( i = 0; i < kNumPanels; i++ ) {
			thePanels[ i ] = new BrowserTestPanel( i );
			add( "Center", thePanels[ i ] );
		}

		upALevelButton = new Button( "Up A Level" );
		add( "Center", upALevelButton );
	}

	public void paint( Graphics g ) {
		refreshCurrentDisplay();
	}

	public boolean handleEvent( Event ev ) {
		int			i;

		if ( ev.id == Event.ACTION_EVENT ) {
			if ( ev.target == upALevelButton ) {
				if ( currentLevel > 0 )
					--currentLevel;

				refreshCurrentDisplay();

				return true;
			}
		}

		if ( ev.id == Event.MOUSE_UP ) {
			for ( i = 0; i < kNumPanels; i++ ) {
				if ( ev.target == thePanels[ i ] ) {
					++currentLevel;
					refreshCurrentDisplay();
					return true;
				}
			}
		}

		if ( ev.id == Event.WINDOW_DESTROY ) {
			System.exit( 0 );
			return true;
		}

		return false;
	}
}

