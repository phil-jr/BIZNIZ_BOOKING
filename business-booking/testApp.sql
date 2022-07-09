CREATE DATABASE IF NOT EXISTS sched;

USE sched;

CREATE TABLE Addresses (
    AddressId VARCHAR(16) NOT NULL PRIMARY KEY,
    AddresLine1 VARCHAR(255) NOT NULL,
    AddresLine2 VARCHAR(255) NULL,
    AddresLine3 VARCHAR(255) NULL,
    City VARCHAR(255) NOT NULL,
    State VARCHAR(2) NOT NULL,
    PostalCode VARCHAR(10) NOT NULL,
    Country VARCHAR(3) NOT NULL
);

CREATE TABLE Attachments (
    AttachmentId VARCHAR(16) NOT NULL PRIMARY KEY,
    FilePath VARCHAR(255) NOT NULL,
    AttachmentTimeStamp DATETIME NOT NULL
);

CREATE TABLE Categories (
    CategoryId INT(11) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Description VARCHAR(255) NULL
);

CREATE TABLE Businesses (
    BusinessId VARCHAR(16) NOT NULL PRIMARY KEY,
    Name VARCHAR(150) NOT NULL,
    Email VARCHAR(80) NOT NULL,
    PhoneNumber VARCHAR(15) NULL,
    Username VARCHAR(20) NOT NULL,
    Password VARCHAR(100) NOT NULL,
    Description VARCHAR(255) NULL,
    Address VARCHAR(16) FOREIGN KEY REFERENCES Addresses(AddressId),
    CategoryId INT(11) FOREIGN KEY REFERENCES Categories(CategoryId),
    ProfilePictureAttachment VARCHAR(16) NOT NULL FOREIGN KEY REFERENCES Attachments(AttachmentId),
    SignUpDate DATETIME NOT NULL
);

CREATE TABLE Customers (
    CustomerId VARCHAR(16) NOT NULL PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(80) NOT NULL,
    Username VARCHAR(20) NOT NULL,
    Password VARCHAR(100) NOT NULL,
    ProfilePictureAttachment VARCHAR(16) NOT NULL FOREIGN KEY REFERENCES Attachments(AttachmentId),
    SignUpDate DATETIME NOT NULL
);

CREATE TABLE BusinessSessionTable (
    SessionId VARCHAR(16) NOT NULL PRIMARY KEY,
    BusinessId VARCHAR(16) NOT NULL FOREIGN KEY REFERENCES Businesses(BusinessId),
    SessionTimestamp DATETIME NOT NULL
);

CREATE TABLE CustomerSessionTable (
    SessionId VARCHAR(16) NOT NULL PRIMARY KEY,
    CustomerId VARCHAR(16) NOT NULL FOREIGN KEY REFERENCES Customers(CustomerId),
    SessionTimestamp DATETIME NOT NULL
);

CREATE TABLE BusinessCustomer (
    BusinessId VARCHAR(16) NOT NULL FOREIGN KEY REFERENCES Businesses(BusinessId),
    CustomerId VARCHAR(16) NOT NULL FOREIGN KEY REFERENCES Customers(CustomerId),
    CONSTRAINT PK_BusinessCustomer PRIMARY KEY (BusinessId, CustomerId)
);

CREATE TABLE Forms (
    FormId VARCHAR(16) NOT NULL PRIMARY KEY,
    BusinessId VARCHAR(16) NOT NULL FOREIGN KEY REFERENCES Businesses(BusinessId),
    FormName VARCHAR(50) NOT NULL,
    FormCreationDate DATETIME NOT NULL
);

CREATE TABLE FormContent (
    FormContentId VARCHAR(16) NOT NULL PRIMARY KEY,
    FormId VARCHAR(16) NOT NULL,
    InputType VARCHAR(16) NOT NULL,
    InputName VARCHAR(16) NOT NULL
);

CREATE TABLE FormData (
    FormDataId VARCHAR(16) NOT NULL PRIMARY KEY,
    CustomerId VARCHAR(16) NOT NULL FOREIGN KEY REFERENCES Customers(CustomerId),
    FormContentId VARCHAR(16) NOT NULL FOREIGN KEY REFERENCES FormContentId(FormContentId),
    FormDataAsString VARCHAR(250) NOT NULL,
    DataCreationDate DATETIME NOT NULL
);


-- SELECT Forms.FormName, Customers.FirstName, Customers.LastName, FormContent.InputName, FormData.FormDataAsString FROM FormData
-- JOIN FormContent ON FormData.FormContentId = FormContent.FormContentId
-- JOIN Forms ON FormContent.FormId = Forms.FormId
-- JOIN Customers ON FormData.CustomerId = Customers.CustomerId
-- WHERE Forms.FormId = 'abc';
