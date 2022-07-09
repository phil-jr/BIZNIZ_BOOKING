CREATE DATABASE IF NOT EXISTS sched;
USE sched;

CREATE TABLE IF NOT EXISTS Addresses (
    AddressId VARCHAR(16) NOT NULL,
    AddresLine1 VARCHAR(255) NOT NULL,
    AddresLine2 VARCHAR(255) NULL,
    AddresLine3 VARCHAR(255) NULL,
    City VARCHAR(255) NOT NULL,
    State VARCHAR(2) NOT NULL,
    PostalCode VARCHAR(10) NOT NULL,
    Country VARCHAR(3) NOT NULL,
    PRIMARY KEY (AddressId)
);


CREATE TABLE IF NOT EXISTS Attachments (
    AttachmentId VARCHAR(16) NOT NULL,
    FilePath VARCHAR(255) NOT NULL,
    AttachmentTimeStamp DATETIME NOT NULL,
    PRIMARY KEY (AttachmentId)
);

CREATE TABLE IF NOT EXISTS Categories (
    CategoryId INT NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Description VARCHAR(255) NULL,
    PRIMARY KEY (CategoryId)
);

CREATE TABLE IF NOT EXISTS Businesses (
    BusinessId VARCHAR(16) NOT NULL,
    Name VARCHAR(150) NOT NULL,
    Email VARCHAR(80) NOT NULL,
    PhoneNumber VARCHAR(15) NULL,
    Username VARCHAR(20) NOT NULL,
    Password VARCHAR(100) NOT NULL,
    Description VARCHAR(255) NULL,
    Address VARCHAR(16),
    CategoryId INT,
    ProfilePictureAttachment VARCHAR(16) NOT NULL,
    SignUpDate DATETIME NOT NULL,
    PRIMARY KEY (BusinessId),
	FOREIGN KEY (Address) REFERENCES Addresses(AddressId),
	FOREIGN KEY (CategoryId) REFERENCES Categories(CategoryId),
    FOREIGN KEY (ProfilePictureAttachment) REFERENCES Attachments(AttachmentId)
);

CREATE TABLE IF NOT EXISTS Customers (
    CustomerId VARCHAR(16) NOT NULL,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(80) NOT NULL,
    Username VARCHAR(20) NOT NULL,
    Password VARCHAR(100) NOT NULL,
    ProfilePictureAttachment VARCHAR(16) NOT NULL,
    SignUpDate DATETIME NOT NULL,
    PRIMARY KEY (CustomerId),
    FOREIGN KEY (ProfilePictureAttachment) REFERENCES Attachments(AttachmentId)
);

CREATE TABLE IF NOT EXISTS BusinessSessionTable (
    SessionId VARCHAR(16) NOT NULL,
    BusinessId VARCHAR(16) NOT NULL,
    SessionTimestamp DATETIME NOT NULL,
    PRIMARY KEY (SessionId),
    FOREIGN KEY (BusinessId) REFERENCES Businesses(BusinessId)
);

CREATE TABLE IF NOT EXISTS CustomerSessionTable (
    SessionId VARCHAR(16) NOT NULL,
    CustomerId VARCHAR(16) NOT NULL,
    SessionTimestamp DATETIME NOT NULL,
    PRIMARY KEY (SessionId),
    FOREIGN KEY(CustomerId) REFERENCES Customers(CustomerId)
);

CREATE TABLE IF NOT EXISTS  BusinessCustomer (
    BusinessId VARCHAR(16) NOT NULL,
    CustomerId VARCHAR(16) NOT NULL,
    CONSTRAINT PK_BusinessCustomer PRIMARY KEY (BusinessId, CustomerId),
    FOREIGN KEY (BusinessId) REFERENCES Businesses(BusinessId),
    FOREIGN KEY (CustomerId) REFERENCES Customers(CustomerId)
);

CREATE TABLE  IF NOT EXISTS Forms (
    FormId VARCHAR(16) NOT NULL,
    BusinessId VARCHAR(16) NOT NULL,
    FormName VARCHAR(50) NOT NULL,
    FormCreationDate DATETIME NOT NULL,
    PRIMARY KEY (FormId),
    FOREIGN KEY (BusinessId) REFERENCES Businesses(BusinessId)
);

CREATE TABLE IF NOT EXISTS FormContent (
    FormContentId VARCHAR(16) NOT NULL,
    FormId VARCHAR(16) NOT NULL,
    InputType VARCHAR(16) NOT NULL,
    InputName VARCHAR(16) NOT NULL,
    PRIMARY KEY (FormContentId)
);

CREATE TABLE IF NOT EXISTS FormData (
    FormDataId VARCHAR(16) NOT NULL,
    CustomerId VARCHAR(16) NOT NULL,
    FormContentId VARCHAR(16) NOT NULL,
    FormDataAsString VARCHAR(250) NOT NULL,
    DataCreationDate DATETIME NOT NULL,
    PRIMARY KEY (FormDataId),
    FOREIGN KEY (CustomerId) REFERENCES Customers(CustomerId),
    FOREIGN KEY (FormContentId) REFERENCES FormContent(FormContentId)
);

CREATE TABLE IF NOT EXISTS scheduling (
    ScheduleId VARCHAR(16) NOT NULL,
    FormId VARCHAR(16) NOT NULL,
    CustomerId VARCHAR(16) NOT NULL,
    TimeSlotBegin DATETIME NOT NULL,
    TimeSlotEnd DATETIME NOT NULL,
    Available BOOLEAN,
    PRIMARY KEY (ScheduleId)
    FOREIGN KEY (FormId) REFERENCES Forms(FormId),
    FOREIGN KEY (CustomerId) REFERENCES Customers(CustomerId)
);


-- SELECT Forms.FormName, Customers.FirstName, Customers.LastName, FormContent.InputName, FormData.FormDataAsString FROM FormData
-- JOIN FormContent ON FormData.FormContentId = FormContent.FormContentId
-- JOIN Forms ON FormContent.FormId = Forms.FormId
-- JOIN Customers ON FormData.CustomerId = Customers.CustomerId
-- WHERE Forms.FormId = 'abc';
