/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.developerstudio.datamapper.diagram.custom.util;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.datamapper.A;

public class AddNewObjectDialog extends Dialog {

	private Text textTitle;
	private Combo comboSchemaType;
	private Text textSchemaValue;
	private Text textID;
	private Text textRequired;
	private Composite compositeType;

	private Label lblTitleLabel;
	private Label lblSchemaTypeLabel;
	private Label lblID;
	private Label lblSchemaValue;
	private Label lblRequired;

	private String title;
	private String schemaType;
	private String schemaValue;
	private String id;
	private String required;

	private String[] DATA_TYPES = { "string", "int", "boolean", "bytes", "double", "enum", "fixed", "float", "long",
			"map", "union" };
	private String[] OBJECT_TYPE = { "object" };
	private String[] ARRAY_TYPE = { "array" };

	// FIXME only RECORD is allowed when adding the list
	// private String DATA_TYPE = "object";

	private static final String DIALOG_TITLE_ROOT = "Add new Root Element";
	private static final String DIALOG_TITLE_OBJECT = "Add new Object";
	private static final String DIALOG_TITLE_ARRAY = "Add new Array";
	private static final String DIALOG_TITLE_FIELD = "Add new Field";
	private static final String DIALOG_TITLE_ATTRIBUTE = "Add new Attribute";
	private static final String DIALOG_TITLE_EDIT_OBJECT = "Edit Object";
	private static final String DIALOG_TITLE_EDIT_ATTRIBUTE = "Edit Attribute";
	private static final String LABEL_TITLE = "Name :";
	private static final String LABEL_SCHEMATYPE = "Schema Type :";
	private static final String LABEL_SCHEMA_VALUE = "Schema Value :";
	private static final String LABEL_REQUIRED = "Required :";
	private static final String LABEL_ID = "ID :";
	private static final String NEW_ROOT_RECORD_ID = "NewRootType";
	private static final String NEW_OBJECT_ID = "NewObject";
	private static final String NEW_ARRAY_ID = "NewArray";
	private static final String NEW_ELEMENT_ID = "NewElement";
	private static final String NEW_ATTRIBUTE_ID = "NewAttribute";
	
	private static final String ARRAY = "array";
	private static final String OBJECT = "object";

	private boolean okPressed = false;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public AddNewObjectDialog(Shell parentShell, Class<?>[] type) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.OK | SWT.APPLICATION_MODAL);
	}

	/**
	 * Sets the title
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		getShell().setText(title);
		// Sets the default value based on the Title
		if (title.equals(DIALOG_TITLE_ROOT)) {
			textTitle.setText(NEW_ROOT_RECORD_ID);
		}if (title.equals(DIALOG_TITLE_OBJECT)) {
			textTitle.setText(NEW_OBJECT_ID);
		}if (title.equals(DIALOG_TITLE_ARRAY)) {
			textTitle.setText(NEW_ARRAY_ID);
		}if (title.equals(DIALOG_TITLE_FIELD)) {
			textTitle.setText(NEW_ELEMENT_ID);
		}if (title.equals(DIALOG_TITLE_ATTRIBUTE)){
			textTitle.setText(NEW_ATTRIBUTE_ID);
		}
	}

	/**
	 * Sets the visibility
	 * 
	 * @param title
	 */
	public void setVisibility(String title) {
		getShell().setText(title);
		// Sets the visibility based on the type
		if (title.equals(DIALOG_TITLE_ROOT)) {
			lblSchemaValue.setVisible(true);
			textSchemaValue.setVisible(true);
		}
		if (title.equals(DIALOG_TITLE_OBJECT) || title.equals(DIALOG_TITLE_ARRAY) || title.equals(DIALOG_TITLE_FIELD)
				|| title.equals(DIALOG_TITLE_ATTRIBUTE)) {
			lblSchemaValue.setVisible(false);
			textSchemaValue.setVisible(false);
		} if (title.equals(DIALOG_TITLE_FIELD)|| title.equals(DIALOG_TITLE_ATTRIBUTE) ) {
			lblRequired.setVisible(false);
			textRequired.setVisible(false);
		}
	}
	
	/**
	 * Sets the type when adding
	 * 
	 * @param title
	 */
	public void setType(String title){
			getShell().setText(title);
		    //Sets the visibility based on the type
		    if(title.equals(DIALOG_TITLE_ROOT) || title.equals(DIALOG_TITLE_OBJECT)){
		    	comboSchemaType.setItems(OBJECT_TYPE);                       
                comboSchemaType.select(0);
		    }else if(title.equals(DIALOG_TITLE_ARRAY)){
		    	comboSchemaType.setItems(ARRAY_TYPE);                       
                comboSchemaType.select(0);
		    }else{
		    	comboSchemaType.setItems(DATA_TYPES);                       
                comboSchemaType.select(0);
		    }
	}


	/**
	 * Sets the the schema type
	 * 
	 * @param title
	 */
	public void setTypeWhenEditing(String type) {
		if(type.equals(OBJECT)){
			comboSchemaType.setItems(OBJECT_TYPE);
		}else if(type.equals(ARRAY)){
			comboSchemaType.setItems(ARRAY_TYPE);
		}else{
			comboSchemaType.setItems(DATA_TYPES);
		}
		
	}

	/**
	 * Sets the values
	 * 
	 * @param title
	 *            title
	 * @param schemaType
	 *            schemaType
	 * @param id
	 *            id
	 * @param required
	 *            required
	 * @param schemaValue
	 *            schemaValue
	 */
	public void setValues(String title, String schemaType, String id, String required, String schemaValue) {
		if (StringUtils.isNotEmpty(title)) {
			textTitle.setText(title);
		}
		if (StringUtils.isNotEmpty(schemaType)) {
			switch (schemaType) {
			case "object":
				comboSchemaType.select(0);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(true);
				textRequired.setVisible(true);
				lblSchemaValue.setVisible(true);
				textSchemaValue.setVisible(true);
				break;
			case "array":
				comboSchemaType.select(0);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(true);
				textRequired.setVisible(true);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "string":
				comboSchemaType.select(0);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "int":
				comboSchemaType.select(1);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "boolean":
				comboSchemaType.select(2);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "bytes":
				comboSchemaType.select(3);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "double":
				comboSchemaType.select(4);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "enum":
				comboSchemaType.select(5);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "fixed":
				comboSchemaType.select(6);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "float":
				comboSchemaType.select(7);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "long":
				comboSchemaType.select(8);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "map":
				comboSchemaType.select(9);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			case "union":
				comboSchemaType.select(10);
				lblID.setVisible(true);
				textID.setVisible(true);
				lblRequired.setVisible(false);
				textRequired.setVisible(false);
				lblSchemaValue.setVisible(false);
				textSchemaValue.setVisible(false);
				break;
			default:
				break;
			}

		}
		if (StringUtils.isNotEmpty(id)) {
			textID.setText(id);
		}
		if (StringUtils.isNotEmpty(required)) {
			textRequired.setText(required);
		}
		if (StringUtils.isNotEmpty(schemaValue)) {
			textSchemaValue.setText(schemaValue);
		}
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		compositeType = new Composite(container, SWT.NONE);
		GridData gd_composite_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_2.widthHint = 575;
		compositeType.setLayoutData(gd_composite_2);
		compositeType.setLayout(new GridLayout(4, false));

		lblTitleLabel = new Label(compositeType, SWT.NONE);
		lblTitleLabel.setText(LABEL_TITLE);

		textTitle = new Text(compositeType, SWT.BORDER);

		textTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {

			}
		});
		textTitle.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));

		lblSchemaTypeLabel = new Label(compositeType, SWT.NONE);
		lblSchemaTypeLabel.setText(LABEL_SCHEMATYPE);

		comboSchemaType = new Combo(compositeType, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboSchemaType.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
			}
		});

		comboSchemaType.setItems(DATA_TYPES);
		comboSchemaType.select(0);

		comboSchemaType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		lblID = new Label(compositeType, SWT.NONE);
		lblID.setText(LABEL_ID);

		textID = new Text(compositeType, SWT.BORDER);
		textID.setText("");

		textID.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {

			}
		});

		textID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		lblRequired = new Label(compositeType, SWT.NONE);
		lblRequired.setText(LABEL_REQUIRED);

		textRequired = new Text(compositeType, SWT.BORDER);
		textRequired.setText("");

		textRequired.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {

			}
		});

		textRequired.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		lblSchemaValue = new Label(compositeType, SWT.NONE);
		lblSchemaValue.setText(LABEL_SCHEMA_VALUE);

		textSchemaValue = new Text(compositeType, SWT.BORDER);
		textSchemaValue.setText("");

		textSchemaValue.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {

			}
		});

		textSchemaValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(620, 280);
	}

	@Override
	protected void okPressed() {
		setOkValue(true);
		setTitleValue(textTitle.getText());
		setSchemaType(comboSchemaType.getText());
		setSchemaValue(textSchemaValue.getText());
		setRequired(textRequired.getText());
		setID(textID.getText());
		super.okPressed();
	}

	public void setTitleValue(String title) {
		this.title = title;
	}

	public void setSchemaType(String schemaType) {
		this.schemaType = schemaType;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setSchemaValue(String schemaValue) {
		this.schemaValue = schemaValue;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setOkValue(boolean okPressed) {
		this.okPressed = okPressed;
	}

	public String getTitle() {
		return title;
	}

	public String getSchemaType() {
		return schemaType;
	}

	public String getID() {
		return id;
	}

	public String getSchemaValue() {
		return schemaValue;
	}

	public boolean getOkValue() {
		return okPressed;
	}

	public String getRequired() {
		return required;
	}

}