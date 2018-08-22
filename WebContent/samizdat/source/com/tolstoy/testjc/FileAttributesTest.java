package com.tolstoy.testjc;

import java.awt.*;
import java.io.*;
import com.jconfig.*;


/**
This dialog shows the attributes for files and directories.

<P>
See DiskFile.java for a list of these attributes.

<P>
This dialog also allows you to set the attributes for a file or directory.

<P>
Instructions:

<OL>
<LI>Run this sample the same way as the other samples are run: using a batch file on Win, shell script on 'Nix, and
JBindery on Mac. Follow the instructions for running Tester.java in wininfo.html, macinfo.html or nixinfo.html,
just instead of using 'com.tolstoy.testjc.Tester', use 'com.tolstoy.testjc.FileAttributesTest' instead

<LI>When this is run, a dialog is shown with the various file attributes.

<LI>Use the 'Choose the file' button to select a file.

<LI>The parts of the dialog are as follows:

	<UL>
	<LI>"ON" or "OFF" is printed in front of each attribute, indicating whether that attribute is on or off.
	<LI>'Setabl' and 'Getabl' follow the name of each attribute, and indicate whether that attribute is
		settable or gettable using DiskFile.setFlags() and DiskFile.getFlags().
		These values were obtained using DiskFile.getSetFlagsMask() and DiskFile.getGetFlagsMask().
	<LI>Two checkboxes follow. The first checkbox is used to make the mask that is passed to DiskFile.setFlags(),
		and the second checkbox is used to make the value.
		If the first checkbox is not checked, when you press the 'Set' button that attribute will not be changed.
		However, if the first checkbox is checked, when you press the 'Set' button, the attribute
		will be set to the value of the second checkbox. That is, if the second checkbox is unchecked, the attribute
		will be set to 0.
	</UL>

<LI>The 'Set' button sets the attributes for the currently selected file, see above for the use of the
	'set mask' and 'set value' checkboxes.

<LI>If you check 'Use container', the selected file's container will be shown.
</OL>
*/

class FileAttributesTest extends Frame {
	private static final String attrNames[] = {
		"EXECUTABLE          ",
		"DIR                 ",
		"HIDDEN              ",
		"STATIONERY          ",

		"HAS_BNDL            ",
		"BEEN_INITED         ",
		"NO_INITS            ",
		"SHARED              ",

		"NAME_LOCKED         ",
		"CUSTOM_ICON         ",
		"SYSTEM              ",
		"ARCHIVE             ",

		"DEVICE              ",
		"TEMP                ",
		"SPARSE              ",
		"REPARSEPOINT        ",

		"COMPRESSED          ",
		"OFFLINE             ",
		"NOT_CONTENT_INDEXED ",
		"ENCRYPTED           ",

		"READONLY            "
	};

	private static final int attrValues[] = {
		0x01,
		0x02,
		0x04,
		0x08,

		0x10,
		0x20,
		0x40,
		0x80,

		0x100,
		0x200,
		0x400,
		0x800,

		0x1000,
		0x2000,
		0x4000,
		0x8000,

		0x10000,
		0x20000,
		0x40000,
		0x80000,

		0x100000
	};

	private static final String		kHelpText = "Choosing a file shows the attributes.\n" +
												"To set the attributes, select the mask bits, and the new values.\n" +
												"Check 'use container' to use the container of the selected file.\n";

	DiskObject					currentDiskObject = null;
	FileAttributesRow			theRows[];
	Button						btnSet, btnChoose;
	Checkbox					checkUseContainer;
	Label						labelResults;

	public FileAttributesTest() {
		File				curDir;
		
		try {
			curDir = new File( System.getProperty( "user.dir" ) );

			FileRegistry.initialize( curDir, 0 );
			if ( !FileRegistry.isInited() ) {
				Trace.println( "Please check your configuration." );
				return;
			}
	
			Trace.setDestination( Trace.TRACE_SYSOUT );
		}
		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}

		setLayout( new BorderLayout() );

		theRows = new FileAttributesRow[ attrValues.length ];

		add( "North", makeControlPanel() );
		add( "Center", makeAttrsPanel() );
	}

	private void chooseButtonHit() {
		FileAttributesRow		row;
		File					curDir, selectedFile;
		String					fileName = "";
		int						i, flags, getFlags, setFlags;

		try {
			fileName = JUtils.getFileNameFromUser();
			if ( fileName == null ) {
				Trace.println( "-- cancelled --" );
				return;
			}

			selectedFile = new File( fileName );

			currentDiskObject = FileRegistry.createDiskObject( selectedFile, 0 );

			if ( checkUseContainer.getState() )
				currentDiskObject = currentDiskObject.getContainer();

			if ( currentDiskObject == null ) {
				labelResults.setText( "Sorry, we're at the top level" );
				return;
			}

			currentDiskObject.dumpInfo( Trace.getOut(), "" );

			flags = currentDiskObject.getFlags();
			getFlags = currentDiskObject.getGetFlagsMask();
			setFlags = currentDiskObject.getSetFlagsMask();
			System.out.println( "getFlags=" + getFlags + ", setFlags=" + setFlags );

			for ( i = 0; i < attrValues.length; i++ ) {
				row = theRows[ i ];

				if ( ( row.value & flags ) != 0 ) {
					row.labelOnOff.setText( " ON" );
					row.labelOnOff.setForeground( Color.red );
					row.checkSet.setState( true );
					row.checkMask.setState( true );
				}
				else {
					row.labelOnOff.setText( "OFF" );
					row.labelOnOff.setForeground( Color.black );
					row.checkSet.setState( false );
					row.checkMask.setState( false );
				}

				if ( ( row.value & getFlags ) != 0 )
					row.labelGettable.setText( "Getabl" );
				else
					row.labelGettable.setText( "-" );

				if ( ( row.value & setFlags ) != 0 ) {
					row.checkSet.setForeground( Color.red );
					row.checkMask.setForeground( Color.red );
					row.checkSet.enable();
					row.checkMask.enable();
					row.labelSettable.setText( "Setabl" );
				}
				else {
					row.checkSet.setForeground( Color.black );
					row.checkMask.setForeground( Color.black );
					row.checkSet.disable();
					row.checkMask.disable();
					row.labelSettable.setText( "-" );
				}
			}

			labelResults.setText( "data for " + currentDiskObject.getFile().getAbsolutePath() );
		}
		catch ( Exception e ) {
			labelResults.setText( "problems with " + fileName +", e=" + e );
			Trace.println( "-- file problems --" + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}
	}

	private void setButtonHit() {
		FileAttributesRow		row;
		int						i, newFlags, mask;

		if ( currentDiskObject == null )
			return;

		newFlags = 0;
		mask = 0;

		try {
			for ( i = 0; i < attrValues.length; i++ ) {
				row = theRows[ i ];

				if ( row.checkMask.getState() ) {
					mask |= row.value;
					if ( row.checkSet.getState() )
						newFlags |= row.value;
				}
			}

			System.out.println( "mask=" + Integer.toHexString( mask ) + ", newFlags=" + Integer.toHexString( newFlags ) );

			currentDiskObject.setFlags( mask, newFlags );
		}
		catch ( Exception e ) {
			labelResults.setText( "problems with " + currentDiskObject.getDisplayName() +", e=" + e );
			Trace.println( "-- file problems --" + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}
	}

	public boolean handleEvent( Event ev ) {
		int			i;

		if ( ev.id == Event.ACTION_EVENT ) {
			if ( ev.target == btnSet ) {
				setButtonHit();
				return true;
			}
		}

		if ( ev.id == Event.ACTION_EVENT ) {
			if ( ev.target == btnChoose ) {
				chooseButtonHit();
				return true;
			}
		}

		if ( ev.id == Event.WINDOW_DESTROY ) {
			System.exit( 0 );
			return true;
		}

		return false;
	}

	private Panel makeControlPanel() {
		Panel			retPanel, northPanel, centerPanel, southPanel;

		retPanel = new Panel();
		northPanel = new Panel();
		centerPanel = new Panel();
		southPanel = new Panel();

		btnSet = new Button( "Set" );
		btnChoose = new Button( "Choose the file" );
		checkUseContainer = new Checkbox( "Use container" );
		labelResults = new Label( "See FileAttributesTest.java for usage instructions" );

		northPanel.add( btnSet );
		northPanel.add( btnChoose );
		northPanel.add( checkUseContainer );

		retPanel.setLayout( new BorderLayout() );

		retPanel.add( "North", northPanel );
		retPanel.add( "South", labelResults );
 
		return retPanel;
	}

	private Panel makeAttrsPanel() {
		Panel					retPanel;
		FileAttributesRow		row;
		int						i;

		retPanel = new Panel();
		retPanel.setLayout( null );

		for ( i = 0; i < attrValues.length; i++ ) {
			row = new FileAttributesRow();
			row.value = attrValues[ i ];
			row.labelName.setText( attrNames[ i ] );

			retPanel.add( row );

			row.reshape( 0, 20 * i, 700, 20 );

			theRows[ i ] = row;
		}

		retPanel.reshape( 0, 0, 700, 24 * attrValues.length );

		return retPanel;
	}

	public static void main( String args[] ) {
		FileAttributesTest		fm;

		fm = new FileAttributesTest();
		fm.pack();
		fm.show();
	}
}

class FileAttributesRow extends Panel {
	Checkbox		checkSet, checkMask;
	Label			labelOnOff, labelName, labelSettable, labelGettable;
	int				value;

	FileAttributesRow() {
		setLayout( null );

		labelOnOff = new Label( "OFF " );
		labelOnOff.reshape( 0, 0, 40, 15 );

		labelName = new Label( "" );
		labelName.reshape( 40, 0, 160, 15 );

		labelSettable = new Label( "-" );
		labelSettable.reshape( 200, 0, 50, 15 );

		labelGettable = new Label( "-" );
		labelGettable.reshape( 250, 0, 50, 15 );

		checkMask = new Checkbox( "set mask" );
		checkMask.reshape( 300, 0, 100, 15 );

		checkSet = new Checkbox( "set value" );
		checkSet.reshape( 400, 0, 100, 15 );

		add( labelOnOff );
		add( labelName );
		add( labelSettable );
		add( labelGettable );
		add( checkMask );
		add( checkSet );

		reshape( 0, 0, 500, 20 );
	}
}




